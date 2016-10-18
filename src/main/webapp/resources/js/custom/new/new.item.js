/*<![CDATA[*/
$(function(){
    var magic =/*[[@{context:}]]*/'', editMode = false, lang = $.cookie("app");
    if(lang == undefined || lang.length < 2) lang = 'en';
    var locale = getLocale(lang), menClothesPattern = '',
        treeData = [], temporaryGroup = {}, gender = 0, x_buff='', cat, item, group_code = -1, itemGroup_id, sz_edit,
        pattern, featuresOk = false, files=false,item_name_en = $('#item-name-en'), item_name_ru = $('#item-name-ru'),
        item_name_ua =$('#item-name-ua'), quantity_input = $('#add_size_panel_qty_li').find('input'),
        origin_price_input = $('#origin_price'), sale_price_input = $('#sale_price'),
        add_size_button = $('#add_size_panel_btn_li').find('button'), save_button = $('#save'),
        csrf = {
            token : $("meta[name='_csrf']").attr("content"),
            header : $("meta[name='_csrf_header']").attr("content")
        },
        levels = [{},{},
        {
            header_li: $('.s2'),
            level_div: $('#level-2'),
            headerText: {locale_en: 'Category', locale_ru:'Категория', locale_ua:'Категорія'},
            level: 2,
            root: true,
            init: false
        },
        {
            header_li: $('.s3'),
            level_div: $('#level-3'),
            headerText: {locale_en: '.', locale_ru:'.', locale_ua:'.'},
            level: 3,
            root: false
        },
        {
            header_li: $('.s4'),
            level_div: $('#level-4'),
            headerText: {locale_en: '.', locale_ru:'.', locale_ua:'.'},
            level: 4,
            root: false
        }
    ];

    $('#group_two').find('.change').on('click', function(){
        $('#group_two').hide();
        $('#section_2').hide();
        $('#section_3').hide();
        $('#section_4').hide();
        $('.icon-wrapper').remove();
        if(group_code < 0) return;
        else if(group_code < 4) {
            group_1_clear_forms();
            group_1_unbind();
        }else if(group_code < 10){
            group_2_unbind();
            group_2_clear_forms();
        }else if(group_code < 12){
            group_4_clear_forms();
            group_4_unbind();
        }
        closeSession();
        $('#group_one').show();
    });
    function chooseItemGroup(name){
        console.log(name);
        var res = resolveGroupCommonCode(name);
        group_code = res.code;
        if(group_code < 0) return;
        $('#group_two_text').text(menClothesPattern +' > ' + pattern);
        if(!$.isNumeric(group_code) || group_code < 0) console.log('some error');
        else if(group_code < 4) group_1_bind_init();
        else if(group_code < 10) group_2_bind_init();
        else if(group_code < 13) group_4_bind_init();
        $('#section_2').show();
        $('#section_3').show();
        $('#section_4').show();
        $('#group_one').hide();
        $('#group_two').show();
    }

    /* (group 1) jeans, shorts, chinos measurement fields*/
    var gr_1_eu_dropDown = $('#eu_waist_select').find('button.dropbtn'),
        gr_1_waist = $('#group_1_waist_li').find('input'),
        gr_1_length = $('#group_1_length_li').find('input'),
        gr_1_bottom = $('#group_1_bottom_li').find('input');
    /*******************************************/

    /*Check methods and binding/unbinding for Group 1*/
    function conditionForAddSizeButtonGroup_1 (){
        return checkDropdown(gr_1_eu_dropDown) &&
                checkLength(gr_1_waist) && checkLength(gr_1_length) && checkLength(gr_1_bottom)
    }
    function checkDropdown(button){
        return button.text() != locale.label_choose;
    }
    function getEuroSize(cat){
        console.log(cat);
        $.ajax({
            url: magic + "../../manage/ajax/get/eurosize/cat",
            dataType: "",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            data: {cat: cat},
            success: function(result){
                drawEuroSize(result,cat);
            },
            error: function(result){
                console.log('Error while getting eurosizes list =/');
                console.log(result)
            }
        });
    }
    function drawEuroSize(data, cat){
        console.log(data);
        var tmp;
        if(cat == 'BOTTOM' || cat == 'WAIST') tmp = gr_1_eu_dropDown.parent().find('.dropdown-content');
        else if(cat == 'TOP') tmp = gr2_eu_dropDown.parent().find('.dropdown-content');
        else if(cat == 'SHOES' || cat == 'ONESIZE') tmp = gr_4_eu_dropDown.parent().find('.dropdown-content');
        else return;
        tmp.find('.lazy').remove();
        for(var i = 0; i < data.length; i++){
            var val = resolveLocale(data[i].name);
            tmp.append('<a class="lazy '+transliterate(val.replace(/\s/g, ''))+'">'+val+'<input type="number" hidden value="'+data[i].id+'"></a>');
        }
        tmp.find('.lazy').click(function (){
            var t = $(this), dropbtn = tmp.parent().find('.dropbtn');
            dropbtn.text(t.text());
            dropbtn.append('<input type="number" hidden value="'+t.find('input').val()+'">');
            t.hide();
            checkAllFormsGroup();
        })
    }
    function bindItemNameInput(){
        item_name_en.bind('input propertychange', function(){
            checkAllFormsGroup();
        });
        item_name_ua.bind('input propertychange', function(){
            checkAllFormsGroup();
        });
        item_name_ru.bind('input propertychange', function(){
            checkAllFormsGroup();
        });
    }
    function group_1_bind_init (){
        $('#item_group_1').show();
        if(group_code == 3) $('#example_group_11').show();
        else $('#example_group_1').show();
        unlock(add_size_button, false);
        gr_1_waist.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_1_waist);
        });
        gr_1_length.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_1_length);
        });
        gr_1_bottom.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_1_bottom);
        });
        add_size_button.on('click', function(){
            var eu = gr_1_eu_dropDown.text(),
                fields = [
                    {name: 'Waist', value: gr_1_waist.val()},
                    {name: 'Length', value: gr_1_length.val()},
                    {name: 'Bottom', value: gr_1_bottom.val()}
                ],
                size = {
                quantity: parseInt(quantity_input.val()), measurements: fields, eu_size: {id: gr_1_eu_dropDown.find('input').val()}};
            group_1_clear_forms();
            addSizePreview(size, eu, makeFieldString(fields));
            processNewSize(size, eu);
        });
    }
    function group_1_clear_forms (){
        gr_1_eu_dropDown.text(locale.label_choose);
        gr_1_waist.val('');
        gr_1_bottom.val('');
        gr_1_length.val('');
        quantity_input.val(1);
    }
    function group_1_unbind (){
        $('#item_group_1').hide();
        if(group_code == 3) $('#example_group_11').hide();
        else $('#example_group_1').hide();
        gr_1_bottom.unbind();
        gr_1_length.unbind();
        gr_1_waist.unbind();
    }
    /*****************************************/

    /* (group 2) T-Shirts, Vests measurement fields*/
    var gr2_eu_dropDown=$('#item_group_2').find('.eu_select').find('.dropbtn'),
        gr_2_shoulders = $('#group_2_shoulders_li').find('input'),
        gr_2_length = $('#group_2_length_li').find('input'),
        gr_2_chest = $('#group_2_chest_li').find('input');

    /*Check methods and binding/unbinding for Group 2*/
    function conditionForAddSizeButtonGroup_2 (){
        return checkDropdown(gr2_eu_dropDown) && checkLength(gr_2_shoulders) &&
            checkLength(gr_2_length) && checkLength(gr_2_chest)&& checkLength(quantity_input)
    }
    function group_2_bind_init (){
        $('#item_group_2').show();
        if(group_code > 5 && group_code < 10) {
            $('#item_group_3').show();
            $('#example_group_3').show();
        }else $('#example_group_2').show();
        unlock(add_size_button, false);
        gr_2_shoulders.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_2_shoulders);
        });
        gr_2_length.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_2_length);
        });
        gr_2_chest.bind('input propertychange', function(){
            checkAllFormsGroup();
            checkMeasurementInput(gr_2_chest);
        });
        if(group_code > 5 && group_code < 10){
            gr_3_sleeve.bind('input propertychange', function(){
                checkAllFormsGroup();
                checkMeasurementInput(gr_3_sleeve);
            });
        }
        add_size_button.on('click', function(){
            var qty = parseInt(quantity_input.val()), eu = gr2_eu_dropDown.text(),
                fields = [
                {name: 'Shoulders', value: gr_2_shoulders.val()},
                {name: 'Length', value: gr_2_length.val()},
                {name: 'Chest', value: gr_2_chest.val()}
                ],
                size = {
                    quantity: qty, measurements: fields, eu_size: {id: gr2_eu_dropDown.find('input').val()}
                };
            if(group_code > 5) fields.push({name: 'Sleeve', value: gr_3_sleeve.val()});
            group_2_clear_forms();
            addSizePreview(size, eu, makeFieldString(fields));
            processNewSize(size, eu);
        });
    }
    function makeFieldString(fields){
        var r='';
        for(var i = 0; i< fields.length; i++){
            r+=fields[i].value;
            if(i<fields.length-1) r+=' / '
        }
        return r;
    }
    function group_2_clear_forms (){
        quantity_input.val(1);
        gr2_eu_dropDown.text(locale.label_choose);
        gr_2_shoulders.val('');
        gr_2_chest.val('');
        gr_2_length.val('');
        if(group_code > 5 && group_code < 10) gr_3_sleeve.val('');
    }
    function group_2_unbind (){
        $('#item_group_2').hide();
        $('#example_group_2').hide();
        gr_2_shoulders.unbind();
        gr_2_length.unbind();
        gr_2_chest.unbind();
        if(group_code > 5 && group_code < 10){
            $('#example_group_3').hide();
            gr_3_sleeve.unbind();
            $('#item_group_3').hide();
        }
    }
    /*small stuff for group_3*/
    var gr_3_sleeve = $('#group_3_sleeve_li').find('input');

    /*Check methods for Group 3*/
    function conditionForAddSizeButtonGroup_3 (){
        return conditionForAddSizeButtonGroup_2() && checkLength(gr_3_sleeve);
    }
    /*Check methods for Group 4*/
    function conditionForAddSizeButtonGroup_4(){
        return conditionForAddSizeButtonGroup_5() && checkLength(gr_4_inSole);
    }
    function conditionForAddSizeButtonGroup_5(){
        return checkLength(quantity_input) && checkDropdown(gr_4_eu_dropDown);
    }
    /* (group 4 and 5) Shoes measurement fields*/
    var gr_4_eu_dropDown=$('#item_group_4').find('.eu_select').find('.dropbtn'),
        gr_4_inSole = $('#group_4_sole_li').find('input');

    function group_4_bind_init (){
        var t = $('#item_group_4'), cond = (group_code == 10);
        t.show();
        console.log('FUUUUCKK!!!!');
        if(cond) {
            $('#example_group_4').show();
            t.find('.group-4-m').show();
            gr_4_inSole.bind('input propertychange', function(){
                checkMeasurementInput(gr_4_inSole);
                checkAllFormsGroup();
            });
            unlock(add_size_button, false);
        }
        add_size_button.on('click', function(){
            var eu = gr_4_eu_dropDown.text(),
                fields = [];
            if(cond) fields.push({name: "Insole", value: gr_4_inSole.val()});
            var size = {
                    quantity: parseInt(quantity_input.val()), measurements: fields, eu_size: {id: gr_4_eu_dropDown.find('input').val()}};
            group_4_clear_forms(cond);
            addSizePreview(size, eu, makeFieldString(fields));
            processNewSize(size, eu);
        });
    }
    function group_4_clear_forms (){
        gr_4_eu_dropDown.text(locale.label_choose);
        gr_4_inSole.val('');
        quantity_input.val(1);
    }
    function group_4_unbind (){
        var t = $('#item_group_4');
        t.hide();
        t.find('.group-4-m').hide();
        $('#example_group_4').hide();
        gr_4_inSole.unbind();
        add_size_button.off('click');
    }

    /*Common methods*/
    function checkAllFormsGroup (){
        var cond;
        if(group_code < 0) cond = false;
        else if(group_code < 4) cond = conditionForAddSizeButtonGroup_1();
        else if(group_code < 6) cond = conditionForAddSizeButtonGroup_2();
        else if(group_code < 10) cond = conditionForAddSizeButtonGroup_3();
        else if(group_code == 10) cond = conditionForAddSizeButtonGroup_4();
        else if(group_code < 13) cond = conditionForAddSizeButtonGroup_5();
        unlock(add_size_button, cond);
        unlock(save_button, checkForSaveButton());
    }
    function checkForSaveButton (){
        var name = checkItemNameBlock(), size = Object.keys(sizeMap).length > 0, cb = checkColorBrandBlock();
        return name && size && checkPriceInput() && featuresOk && cb && files;
    }

    function checkItemNameBlock(){
        var cond = checkLength(item_name_en) && checkLength(item_name_ru) && checkLength(item_name_ua);
        changeBlockStatus($('#name-block'), cond);
        return cond;
    }
    function checkColorBrandBlock(){
        var brand = $('#brand-logo').find('img').attr('src') != '/atl/storage/brand/images/default.png',
            color = $('#color_sample').data('color'),
            cond = brand && color;
        changeBlockStatus($('#cb-block'), cond);
        return cond;
    }

    function changeBlockStatus(block, cond){
        if(cond) {
            if(block.hasClass('not_completed')){
                block.removeClass('not_completed');
                block.addClass('completed')
            }
        }else{
            if(block.hasClass('completed')){
                block.removeClass('completed');
                block.addClass('not_completed');
            }
        }
    }
    function checkMeasurementInput (input){
        var value = input.val();
        if(value.length == 1 && value =='.' || value =='-') value = '';
        input.val(value.replace(/[^0-9.-]/g,''));
    }
    function checkIntegerInput (input){
        var val = quantity_input.val().replace(/[^0-9]/g,'');
        if(val < 1) val = 1;
        input.val(val);
    }
    function resolveGroupCommonCode (name){
        pattern = name.trim();
        var en = getLocale('en');
        switch (pattern){
            case locale.group_jeans:           return {code: 1, item_group: en.group_jeans};
            case locale.group_chinos:          return {code: 2, item_group: en.group_chinos};
            case locale.group_shorts:          return {code: 3, item_group: en.group_shorts};
            case locale.group_t_shirts:        return {code: 4, item_group: en.group_t_shirts};
            case locale.group_vests:           return {code: 5, item_group: en.group_vests};
            case locale.group_long_sleeves:    return {code: 6, item_group: en.group_long_sleeves};
            case locale.group_shirts:          return {code: 7, item_group: en.group_shirts};
            case locale.group_jumpers:         return {code: 8, item_group: en.group_jumpers};
            case locale.group_jackets:         return {code: 9, item_group: en.group_jackets};
            case locale.group_shoes:           return {code: 10, item_group: en.group_shoes};
            case locale.group_boots:           return {code: 10, item_group: en.group_boots};
            case locale.group_trainers:        return {code: 10, item_group: en.group_trainers};
            case locale.group_accessories:     return {code: 11, item_group: en.group_accessories};
            case locale.group_beanies:         return {code: 12, item_group: en.group_beanies};
            default:                           return -1;
        }
    }
    var sizeMap = [];
    function syncSizeInfo(eu){
        var sz_popup= $('#size_popup');
        sz_popup.find('.dropbtn').text(eu);
        sz_popup.find('#sz-eu-id').val(sizeMap[eu].eu_size.id);
        sz_popup.find('#sz-e-qty').val(sizeMap[eu].quantity);
        for(var i = 0; i< sizeMap[eu].measurements.length; i++ ){
            var t;
            if(i < 3) t = sz_popup.find('.sz-e-fields li:eq('+i+')');
            else if(i < 5) {
                t = sz_popup.find('.sz-e-fields-ex li:eq(0)');
                t.show();
            } else  break;
            t.find('label').text(sizeMap[eu].measurements[i].name);
            t.find('input').val(sizeMap[eu].measurements[i].value);
        }
        sz_popup.popup('show');
    }
    function addSizePreview (size, eu, fieldsString){
        $('#size_preview').find('ul.sz-main').append(
            '<li class="sz-preview-list">\
                <div class="sz-container">\
                    <ul class="list-inline sz-ul">\
                        <li class="sz-eu"><span>'+eu+'</span></li>\
                        <li class="sz-ms"><label class="sz-invisible">'+fieldsString+'</label> </li>\
                    </ul>\
                </div>\
                <div class="sz-qty-tag">'+size.quantity+'</div>\
                <div class="sz-remove sz-invisible"></div>\
                <button class="sz-remove-btn sz-invisible"></button>\
            </li>');
        var thumb = $('.sz-preview-list:last');
        thumb.click(function(){
            sz_edit = $(this);
            syncSizeInfo(eu);
        });
        thumb.mouseenter(function(){
            var inv = $(this).find('.sz-invisible');
            inv.removeClass('sz-invisible');
            inv.addClass('sz-visible')
        });
        thumb.mouseleave(function(){
            var inv = $(this).find('.sz-visible');
            inv.removeClass('sz-visible');
            inv.addClass('sz-invisible')
        });
        thumb.find('.sz-remove-btn').click(function(){
            delete sizeMap[eu];
            thumb.remove();
            var s = '.'+transliterate(eu.replace(/\s/g, ''));
            $(s).show();
            changeBlockStatus($('#sz-block'), Object.keys(sizeMap).length > 0);
            checkAllFormsGroup();
        });
    }
    function processNewSize (size, eu){
        sizeMap[eu] = size;
        console.log(sizeMap[eu]);
        changeBlockStatus($('#sz-block'), true);
        checkAllFormsGroup();
    }
    function featuresJoin(inputs){
        var result='';
        inputs.each(function(){
            var split = '';
            if(result.length > 0) split = '|';
            result = result+split+$(this).val();
        });
        return result;
    }

    function saveClothesItem (){
        var size = [];
        for(var key in sizeMap){
            if(sizeMap.hasOwnProperty(key)){
                size.push(sizeMap[key]);
            }
        }
        item = {
            gender: gender,
            itemName: {
                locale_en: item_name_en.val(),
                locale_ru: item_name_ru.val(),
                locale_ua: item_name_ua.val()
            },
            description:{
                locale_en: featuresJoin($('.f-en')),
                locale_ru: featuresJoin($('.f-ru')),
                locale_ua: featuresJoin($('.f-ua'))
            },
            extraNotes: $('#extraNotes_popup').find('textarea').val(),
            currency: parseInt($('#currency').find('select').val()),
            itemGroup: {
                id: itemGroup_id
            },
            discount: $('input#discount').val(),
            quantity: Object.keys(sizeMap).length,
            color: {id: $('#color_sample').find('input').val()},
            originPrice: parseInt(origin_price_input.val()*100),
            salePrice: parseInt(sale_price_input.val()*100),
            brand: {
                id: $('li#brand').find('.dropbtn').find('input').val()
            },
            size: size
        };
        console.log(JSON.stringify(item));
        $('#item').val(JSON.stringify(item));
        $('#sub').click();
    }
    /********************************************/
    save_button.on('click', function(){
        unlock(save_button, false);
        if(!$.isNumeric(group_code) || group_code < 0) console.log('some error');
        saveClothesItem();
    });
    function resetColorBrand(){
        var elem = $('#color_sample');
        elem.data('color', false);
        elem.css('background', 'white');
        elem = $('#brand');
        elem.find('.dropbtn').text(locale.label_choose);
        elem.parent().find('img').attr('src','/atl/storage/brand/images/default.png');
    }
    function closeSession() {
        changeBlockStatus($('#cat-block'), false);
        changeBlockStatus($('#cb-block'), false);
        changeBlockStatus($('#sz-block'), false);
        changeBlockStatus($('#ds-block'), false);
        changeBlockStatus($('#pr-block'), false);
        $('.sz-preview-list').remove();
        $('ul.feature_preview').remove();
        resetColorBrand();
        $('#filer_input').prop("jFiler").reset();
        add_size_button.off('click');
        $('#discount').val(0);
        group_code = -1;
        temporaryGroup = {};
        quantity_input.val(1);
        origin_price_input.val(0);
        sale_price_input.val(0);
        sizeMap = [];
    }
    function photoInit(files){
        $('#filer_input').filer({
            showThumbs: true,
            limit: 6,
            files: files,
            appendTo: $('#upload_wrapper'),
            templates: {
                box: filer.box,
                item: filer.item,
                itemAppend: filer.itemAppend,
                itemAppendToEnd: true,
                removeConfirmation: true,
                _selectors: {
                    list: '.jFiler-items-list',
                    item: '.jFiler-item',
                    remove: '.jFiler-item-trash-action'
                }
            },
            captions: {
                button: locale.label_addPhotos,
                feedback: "Choose files To Upload",
                feedback2: "files were chosen",
                drop: "Drop file here to Upload",
                removeConfirmation: "Are you sure you want to remove this file?",
                errors: {
                    filesLimit: "Only {{fi-limit}} files are allowed to be uploaded.",
                    filesType: "Only Images are allowed to be uploaded.",
                    filesSize: "{{fi-name}} is too large! Please upload file up to {{fi-maxSize}} MB.",
                    filesSizeAll: "Files you've choosed are too large! Please upload files up to {{fi-maxSize}} MB."
                }
            },
            afterShow: function(){
                changePhotoBlockStatus(true)
            },
            onEmpty: function(){
                changePhotoBlockStatus(false)
            },
            addMore: true
        });
    }

    function changePhotoBlockStatus(cond){
        files = cond;
        changeBlockStatus($('#ph-block'), files);
        checkForSaveButton();
    }
    function checkPriceInput(){
        return (sale_price_input.val() > 0) && (origin_price_input.val() > 0)
    }
    function bindPriceInput(){
        var block = $('#pr-block');
        origin_price_input.bind('input propertychange', function(){
            unlock(save_button, checkForSaveButton());
            changeBlockStatus(block, checkPriceInput())
        });
        sale_price_input.bind('input propertychange', function(){
            unlock(save_button, checkForSaveButton());
            changeBlockStatus(block, checkPriceInput())
        });
    }

    function fillColor(data){
        var content = $('#color').find('.dropdown-content');
        content.find('a.color-option').remove();
        for(var i=0; i<data.length; i++){
            var color = data[i].hex;
            content.append('<a class="color-option">'+resolveLocale(data[i].color)+'<div class="hex" style="background: '+color+'"><input hidden value="'+data[i].id+'"></div></a>')
        }
        $('.color-option').click(function(){
            var t = $(this), text = t.text(), hex = t.find('.hex');
            if(text.length > 15) text = text.substring(0,15) + '.';
            $('#color').find('.dropbtn').text(text);
            text =  $('#color_sample');
            text.css({'background-color':hex.css('background')});
            text.find('input').val(hex.find('input').val());
            text.data('color', true);
            checkAllFormsGroup ();
        });
    }
    $('#add-cat-popup').popup({
        openelement:'.changeGroup',
        closeelement:'.close_group',
        transition: 'all 0.3s'
    });
    var extra_popup = $('#extraNotes_popup');
    extra_popup.popup({
        openelement:'.extra_note',
        closeelement:'.extra_close',
        transition: 'all 0.3s',
        onclose: function(){
            var b = $('.extra_note'), info = $('#ex-info');
            if(info.val().length > 0 && !b.hasClass('extra_note_filled')){
                b.removeClass('extra_note_not_filled');
                b.addClass('extra_note_filled');
            }else if (info.val().length < 1 && !b.hasClass('extra_note_not_filled')){
                b.removeClass('extra_note_filled');
                b.addClass('extra_note_not_filled');
            }
        }
    });

    function fillBrand(data, imageUrl){
        var brandDropdown = $('li#brand').find('.dropdown-content');
        brandDropdown.find('.brand-option').remove();
        for(var i=0; i< data.length; i++){
            brandDropdown.append('<a class="brand-option">' + data[i].name + '<input type="number" hidden value="' + data[i].id + '"></a>')
        }
        $('.brand-option').click(function(){
            var tmp = $(this), dropbtn = tmp.parent().parent().find('.dropbtn');
            dropbtn.text(tmp.text());
            dropbtn.append('<input type="number" hidden value="'+tmp.find('input').val()+'">');
            $('#brand-logo').find('img').attr('src', magic+"../.." + imageUrl+tmp.text().toLowerCase().replace(/\s/g,"_")+'/logo.jpg').show();
            checkAllFormsGroup ();
        });
    }

    function createTreeData(data, parent){
        var result = [];
        for(var i = 0; i< data.length; i++){
            var obj={text:'',level:0,parent:''};
            obj.text = resolveLocale(data[i].groupName);
            obj.level = data[i].level;
            obj.cat =  data[i].cat;
            obj.parent = parent;
            obj.id=data[i].id;
            if(data[i].level < 3) obj.state={expanded: true};
            if(data[i].child.length > 0) {
                obj.childCat = data[i].child[0].cat;
                obj.nodes = (createTreeData(data[i].child, parent +' > ' +obj.text));
            }
            result.push(obj);
        }
        return result;
    }

    function resolveGender(text){
        if(text == 'Men' || text =='Мужское' || text == 'Чоловіче') gender = 0;
        else if(text == 'Women' || text == 'Женское' || text =='Жіноче') gender = 1;
    }

    function drawCategoryPopup(data, lvl, id, cat){
        if(data == undefined) return;
        for(var i=0; i< data.length; i++){
            var child = '<input hidden type="number" value="'+i+'">';
            if(data[i].nodes != undefined && data[i].nodes.length > 0) child= child+'<span class="glyphicon glyphicon-menu-right"></span>';
            lvl.append('<li id="lvl-'+data[i].level+i+'">'+data[i].text+child+'</li>');
            child = '#lvl-'+data[i].level+i;
            if(editMode) {
                var ch = $(child);
                if(data[i].level == 2 && data[i].text == text) {
                    ch.addClass('chosen');
                    resolveActiveDependencies(levels[data[i].level], data[i].text, true);
                    if(data[i].nodes != undefined) drawCategoryPopup(data[i].nodes, levels[data[i].level+1].level_div.find('ul'),undefined, cat);
                }else if(data[i].childCat == cat){
                    ch.addClass('chosen');
                    resolveActiveDependencies(levels[data[i].level], data[i].text, true);
                    if(data[i].nodes != undefined) drawCategoryPopup(data[i].nodes, levels[data[i].level+1].level_div.find('ul'),undefined, cat);
                } else if(data[i].cat == cat){
                    console.log(grName);
                    if(data[i].level == 4 && data[i].cat == cat && data[i].text == grName)  {
                        ch.addClass('chosen');
                        resolveActiveDependencies(levels[data[i].level], data[i].text, true);
                    }
                    if(data[i].nodes != undefined) drawCategoryPopup(data[i].nodes, levels[data[i].level+1].level_div.find('ul'),undefined, cat);
                }else ch.addClass('cat-blocked');
            }
            $(child).click(function(){
                var li = $(this), number = li.find('input').val();
                if(li.hasClass('cat-blocked')) return;
                li.parent().find('.chosen').removeClass('chosen');
                if(data[number].level == 2){
                    resolveGender(data[number].text)
                }
                li.addClass('chosen');
                resolveActiveDependencies(levels[data[number].level], data[number].text,data[number].nodes != undefined);
                if(data[number].nodes == undefined){
                    if(data[number].level == 4){
                        menClothesPattern = data[number].parent;
                        temporaryGroup.text = data[number].text;
                        temporaryGroup.cat = data[number].cat;
                        itemGroup_id = data[number].id;
                    }
                    return;
                }
                drawCategoryPopup(data[number].nodes, levels[data[number].level+1].level_div.find('ul'),undefined, cat);
            })
        }
    }
    $('#accept_group').click(function(){
        changeBlockStatus($('#cat-block'), true);
        chooseItemGroup(temporaryGroup.text);
        getEuroSize(temporaryGroup.cat);
    });

    function showActive(level, text){
        level.header_li.removeClass('stage_ina');
        level.header_li.text(text);
        level.header_li.addClass('stage_comp');
        level.level_div.addClass('completed');
    }
    function hideActive(level){
        level.header_li.text(resolveLocale(level.headerText));
        level.header_li.removeClass('stage_comp');
        level.level_div.removeClass('completed');
        level.level_div.find('li').remove();
        level.header_li.addClass('stage_ina');
    }

    function resolveActiveDependencies(level, text){
        var accept = $('#accept_group');
        switch (level.level){
            case 2:{
                hideActive(levels[3]);
                hideActive(levels[4]);
                showActive(level, text);
                if(!level.init) {
                    document.getElementById('root').removeAttribute('id');
                    level.init = true;
                }
                accept.prop('disabled',true);
                break;
            }
            case 3:{
                hideActive(levels[4]);
                showActive(level, text);
                accept.prop('disabled',true);
                break;
            }
            case 4:{
                showActive(level, text);
                accept.prop('disabled',false);
            }
        }
    }
    $('.featureBlock').bind('input propertychange', function(){
        var ul = $('.item_description');
        ul.parent().find('button.feature').prop('disabled',!(checkLength(ul.find('.ru'))&&checkLength(ul.find('.en'))&&checkLength(ul.find('.ua'))));
    });

    function removeVal(form){
        var val = form.val();
        form.val('');
        return val;
    }
    /**
     * Adding an action on button click for removing row of features;
     * @param button - .remove_row button from current ul.
     */
    function removeRowClick(button, block){
        button.click(function(){
            var list = $(this).parent().parent();
            list.fadeOut(150);
            setTimeout(function(){
                list.remove();
                checkAllFeaturesInputs($('input.val'), block);
            }, 200);
        })
    }

    function checkAllFeaturesInputs(input, block){
        var cond = input.length > 0;
        input.each(function(){
            if($(this).val().length < 1) cond = false;
        });
        featuresOk = cond;
        changeBlockStatus(block, featuresOk);
        unlock(save_button, checkForSaveButton());
    }

    function checkFeatureInputLength(input, block){
        featuresOk = true;
        input.each(function(){
            $(this).bind('input propertychange', function(){
                var currentInput = $(this), cond = currentInput.val().length > 0;
                if(cond) currentInput.removeClass('broken');
                else currentInput.addClass('broken');
                checkAllFeaturesInputs(input, block);
            })
        });
    }

    $('button.feature').click(function(){
        var ul = $('.item_description'), block = $('#ds-block'),
            holder = ul.parent().find('.feature_holder'),
            lastEq = holder.find('ul').length;
        holder.append('<ul class="list-inline feature_preview">' +
            '<li><input type="text" class="height val f-en" value="'+removeVal(ul.find('.en'))+'" spellcheck="false"></li>' +
            '<li class="marg"><input type="text" class="height val f-ru" value="'+removeVal(ul.find('.ru'))+'" spellcheck="false"></li>' +
            '<li class="marg"><input type="text" class="height val f-ua" value="'+removeVal(ul.find('.ua'))+'" spellcheck="false"></li>' +
            '<li><button class="remove_row"></button></li></ul>');
        ul.parent().find('button.feature').prop('disabled', true);
        checkFeatureInputLength(holder.find('input.val'), block);
        ul = holder.find('ul:eq('+lastEq+')');
        removeRowClick(ul.find('button.remove_row'), block);
        changeBlockStatus(block, featuresOk);
        unlock(save_button, checkForSaveButton());
    });
    extra_popup.find('.accept-button').click(function(){
        x_buff = $('#ex-info').val();
    });
    extra_popup.find('.cancel-button').click(function(){
        $('#ex-info').val(x_buff);
    });
    $("#color-sample").spectrum({
        color: "#fff",
        showAlpha: true,
        clickoutFiresChange: false,
        showInitial: true,
        showInput: true,
        preferredFormat: "hex"
    });
    $('.sp-choose').click(function () {
        $('#color-value').text($('.sp-input').val());
    });
    function bindUpdate(popup){
        var cond = popup.name != undefined && popup.files != undefined;
        popup.p.popup({
            opacity: 0.3,
            transition: 'all 0.3s',
            openelement: popup.openelement,
            closeelement: popup.closeelement,
            blur: popup.blur,
            scrolllock: popup.scrollock,
            onclose: function(){
                popup.en.val('');
                popup.ru.val('');
                popup.ua.val('');
                if(cond){
                    popup.name.val('');
                    popup.files.reset();
                }else{
                    popup.p.find('.sp-preview-inner').css('background','#ffffff')
                    popup.p.find('#color-value').text('#ffffff')
                }
            }
        });
        popup.en.bind('input propertychange', function(){unlock(popup.saveButton, checkLength(popup.en) &&
            checkLength(popup.ru) && checkLength(popup.ua) && (cond ? checkLength(popup.name) && popup.files.files_list.length > 0 : true))});
        popup.ru.bind('input propertychange', function(){unlock(popup.saveButton, checkLength(popup.en) &&
            checkLength(popup.ru) && checkLength(popup.ua) && (cond ? checkLength(popup.name) && popup.files.files_list.length > 0 : true))});
        popup.ua.bind('input propertychange', function(){unlock(popup.saveButton, checkLength(popup.en) &&
            checkLength(popup.ru) && checkLength(popup.ua) && (cond ? checkLength(popup.name) && popup.files.files_list.length > 0 : true))});
        if(cond) popup.name.bind('input propertychange', function(){unlock(popup.saveButton, checkLength(popup.en) &&
            checkLength(popup.ru) && checkLength(popup.ua) && checkLength(popup.name) && popup.files.files_list.length > 0)});
    }
    function bindNewColorInput(){
        var p = $('#color_popup'), popup = {p: p, en: p.find('#c-name-en'), ru: p.find('#c-name-ru'), ua: p.find('#c-name-ua'),
            saveButton: p.find('.update-button'), openelement: '#color-plus',  closeelement: '.color_close', blur:false, scrollock: true};
        bindUpdate(popup);
        popup.saveButton.click(function(){
            updateColor(JSON.stringify({
                color: {
                    locale_en: popup.en.val(),
                    locale_ru: popup.ru.val(),
                    locale_ua: popup.ua.val()
                },
                hex: popup.p.find('#color-value').text()
            }))
        });
    }
    function updateColor(color){
        $.ajax({
            url: magic + "../../manage/ajax/save/color",
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            data: color,
            type: "POST",
            success: function(result){
                fillColor(result);
            },
            error: function(result){
                console.log('Error while getting colors =/');
                console.log(result)
            }
        });
    }
    $('#brand_input').filer({
        showThumbs: true,
        limit: 1,
        appendTo: $('#b-file-holder'),
        templates: {
            box: filer.box,
            item: filer.item,
            itemAppend: filer.itemAppend,
            itemAppendToEnd: true,
            removeConfirmation: true,
            _selectors: {
                list: '.jFiler-items-list',
                item: '.jFiler-item',
                remove: '.jFiler-item-trash-action'
            }
        },
        captions: {
            button: locale.label_addPhotos,
            feedback: "Choose files To Upload",
            feedback2: "files were chosen",
            drop: "Drop file here to Upload",
            removeConfirmation: "Are you sure you want to remove this file?",
            errors: {
                filesLimit: "Only {{fi-limit}} files are allowed to be uploaded.",
                filesType: "Only Images are allowed to be uploaded.",
                filesSize: "{{fi-name}} is too large! Please upload file up to {{fi-maxSize}} MB.",
                filesSizeAll: "Files you've choosed are too large! Please upload files up to {{fi-maxSize}} MB."
            }
        },
        afterShow: function(){
            var popup = $('#brand_popup');
            unlock(popup.find('.update-button'), checkLength(popup.find('#b-dsc-en')) && checkLength(popup.find('#b-dsc-ru')) &&
                checkLength(popup.find('#b-dsc-ua')) && checkLength(popup.find('#b-name')))
        },
        onEmpty: function(){
            unlock($('#brand_popup').find('.update-button'), false)
        },
        addMore: false
    });
    function uploadBrand(popup, data){
        var formData = new FormData();
        formData.append('file', popup.p.find('#brand_input')[0].files[0]);
        formData.append('b', JSON.stringify(data));
        $.ajax({
            url: magic + "../../manage/ajax/save/brand",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            contentType: false,
            processData: false,
            cache: false,
            data: formData,
            type: "POST",
            success: function(result){
                fillBrand(result.brandList, result.brandImgUrl)
            },
            error: function(result){
                console.log('Error while updating brand =/');
                console.log(result)
            }
        })
    }
    function bindNewBrandInput(){
        var p = $('#brand_popup');
        var popup = {p: p, en: p.find('#b-dsc-en'), ru: p.find('#b-dsc-ru'),ua: p.find('#b-dsc-ua'), blur: true, saveButton: p.find('.update-button'),
            name: p.find('#b-name'), openelement: '#brand-plus', closeelement: '.brand_close',scrollock: false, files: $('#brand_input').prop("jFiler")};
        bindUpdate(popup);
        popup.saveButton.click(function(){
            uploadBrand(popup, {
                name: popup.name.val(),
                description: {
                    locale_en: popup.en.val(),
                    locale_ru: popup.ru.val(),
                    locale_ua: popup.ua.val()
                }
            });
        })
    }
    function checkForSizeEdit(block,eu, itself){
        for(var i = 0; i< sizeMap[eu].measurements.length; i++){
            var t;
            if(i < 3) t = block.find('.sz-e-fields li:eq('+i+')');
            else if(i < 5) {
                t = block.find('.sz-e-fields-ex li:eq(0)');
            }
            else break;
            t = t.find('input');
            checkMeasurementInput(t);
            if(!checkLength(t)) return false;
        }
        return true;
    }
    $('#size_popup').popup({
        opacity: 0.3,
        transition: 'all 0.3s',
        closeelement: '.size_close',
        onopen:function(){
            var sz_e_popup = $('#size_popup'), sz_e_btn = sz_e_popup.find('.update-button'),
                eu = sz_e_popup.find('.dropbtn').text(), first =  sz_e_popup.find('.first'),
                second = sz_e_popup.find('.second'), third = sz_e_popup.find('.third'),
                fourth = sz_e_popup.find('.fourth'), qty = sz_e_popup.find('#sz-e-qty'), cond = group_code > 5 && group_code < 11;
            qty.bind('input propertychange', function(){
                unlock(sz_e_btn, checkForSizeEdit(sz_e_popup, eu));
            });
            first.bind('input propertychange', function(){
                unlock(sz_e_btn, checkForSizeEdit(sz_e_popup, eu));
            });
            if(group_code < 6){
                second.bind('input propertychange', function(){
                    unlock(sz_e_btn, checkForSizeEdit(sz_e_popup, eu));
                });
                third.bind('input propertychange', function(){
                    unlock(sz_e_btn, checkForSizeEdit(sz_e_popup, eu));
                });
            }
            if(cond){
                if(group_code == 10)  {
                    second.parent().hide();
                    third.parent().hide();
                }
                first.bind('input propertychange', function(){
                    unlock(sz_e_btn, checkForSizeEdit(sz_e_popup, eu));
                });
            }
            else if(!cond && group_code > 10) {
                sz_e_popup.find('.sz-e-fields').hide();
            }
            sz_e_btn.click(function(){
                sizeMap[eu] = {
                    quantity: qty.val(),
                    measurements: [],
                    eu_size:{id: sz_e_popup.find('#sz-eu-id').val()}
                };
                if(group_code < 11)sizeMap[eu].measurements=[{name: first.parent().find('label').text(), value: first.val()}];
                if(group_code < 10) sizeMap[eu].measurements.push(
                    {name: second.parent().find('label').text(),value: second.val()},
                    {name: third.parent().find('label').text(), value: third.val()});
                if(cond && group_code != 10) sizeMap[eu].measurements.push({name: fourth.parent().find('label').text(), value: fourth.val()});
                console.log(sizeMap);
                sz_edit.find('.sz-ms label').text(makeFieldString(sizeMap[eu].measurements));
                sz_edit.find('.sz-eu span').text(eu);
                sz_edit.find('.sz-qty-tag').text(sizeMap[eu].quantity);
            });
        },
        onclose:function(){
            var sz_e_popup = $('#size_popup');
            sz_e_popup.find('#sz-e-qty').unbind();
            sz_e_popup.find('.sz-e-fields').show();
            sz_e_popup.find('.update-button').off('click');
            if(group_code < 11){
                var t =sz_e_popup.find('.first');
                t.unbind();
                t.val('');
                t = sz_e_popup.find('.second');
                t.unbind();
                t.val('');
                t.parent().show();
                t = sz_e_popup.find('.third');
                t.unbind();
                t.val('');
                t.parent().show();
            }
            if(group_code > 5 && group_code < 11){
                sz_e_popup.find('.fourth').unbind();
            }
            sz_e_popup.find('.sz-e-fields-ex li:eq(0)').hide();
        }
    });

    function editModeGetItemInfo(id){
        console.log('id='+id);
        $.ajax({
            url: magic + "../../manage/ajax/get/item/single",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            data: {'id': parseInt(id)},
            type: "POST",
            success: function(result){
                console.log(result);
                fillItemsInfo(result);
            },
            error: function(result){
                console.log(result);
                editMode = false;
                init_0(true)
            }
        });
    }
    var text, grName;
    function fillItemsInfo(item){
        item_name_ru.val(item.itemName.locale_ru);
        item_name_en.val(item.itemName.locale_en);
        item_name_ua.val(item.itemName.locale_ua);
        changeBlockStatus($('#name-block'), true);
        editColorBrand(item);
        editPhotos(item.images);
        text = resolveGenderToText(item.gender, locale);
        grName = resolveLocale(item.itemGroup.groupName);
        categoryInit(item.itemGroup.cat);
        $('.cat-blocked').click(function(){
            $(this).preventDefault();
        })
    }
    function editColorBrand(item){
        var element = $('#brnd').find('.dropbtn');
        changeBlockStatus($('#cb-block'), true);
        element.text(item.brand.name);
        element.append('<input hidden type=number value="'+item.brand.id+'">');
        $('#brand-logo').find('img').attr('src', magic+"../.." + item.brandImgUrl + item.brand.name.toLowerCase().replace(/\s/g,"_")+'/logo.jpg').show();
        element = $('#color').find('.dropbtn');
        element.text(resolveLocale(item.color.color));
        element.append('<input hidden type=number value="'+item.color.id+'">');
        element = $('#color_sample');
        element.data('color', true);
        element.find('input').val(item.color.id);
        element.css({'background-color':item.color.hex});
    }
    function editPhotos(images){
        var files=[];
        for(var i=0; i< images.length;i++){
            files.push({
                name: images[i].substring(images[i].lastIndexOf('/')+1),
                url: magic + '../..'+images[i],
                type: 'image/jpg',
                file: magic+'../..'+images[i],
                size: ''
            });
        }
        console.log(files);
        photoInit(files);
    }

    function categoryInit(cat){
        console.log('cat='+cat);
        $.ajax({
            url: magic + "../../manage/ajax/get/item/options/clothes",
            contentType: 'application/json',
            mimeType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            success: function(result){
                treeData = createTreeData(result.itemGroup.child, 'items');
                setTimeout(function(){
                    console.log(treeData);
                    drawCategoryPopup(treeData,$('#level-2').find('ul'),[3,4], cat);
                },50);
                fillColor(result.colorList);
                fillBrand(result.brandList, result.brandImgUrl);
            },
            error: function(result){
                console.log('Error while getting itemsgroup =/');
                console.log(result)
            }
        });
    }
    /*Entry point main method*/
    function init_0(standart){
        if(getURLParameter('edit') && standart == undefined){
            editMode = true;
            editModeGetItemInfo(getURLParameter('id'));
        }else {
            console.log('edit=false');
            photoInit([]);
            categoryInit('');
        }
        $('.brand-tabs').tabslet();
        $('.color-tabs').tabslet();
        bindPriceInput();
        bindItemNameInput();
        bindNewColorInput();
        bindNewBrandInput();
        quantity_input.bind('input propertychange', function(){
            checkIntegerInput(quantity_input);
        });

        $('.main-content .dropbtn').click(function(e){
            $(this).parent().find('.dropdown-content').toggle(150);
        });

    }
    init_0();
});
/*]]>*/