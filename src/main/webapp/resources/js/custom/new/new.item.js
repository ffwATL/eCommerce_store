/*<![CDATA[*/
$(function(){
    var magic =/*[[@{context:}]]*/'',
        lang = $.cookie("app");
    if(lang == undefined || lang.length < 2) lang = 'en';
    var locale = getLocale(lang), menClothesPattern = '',
        final_sizes = [], deleted = [], all_sizes = [],treeData = [], temporaryGroup = {},
        quantity_sizes = 0, count = 0, count_size = 0, gender = 0, x_buff='',
        cat, item, item_group_code, itemGroup_id, pattern, featuresOk = true,
        item_name_en = $('#item-name-en'), item_name_ru = $('#item-name-ru'), item_name_ua =$('#item-name-ua'),
        quantity_input = $('#add_size_panel_qty_li').find('input'),
        origin_price_input = $('#origin_price'),
        sale_price_input = $('#sale_price'),
        add_size_button = $('#add_size_panel_btn_li').find('button'),
        save_button = $('#save'),
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
        $('#brand').find('select option:eq(0)').prop('selected', true);
        if(item_group_code < 0) return;
        else if(item_group_code < 4) {
            group_1_clear_forms();
            group_1_unbind();
        }else if(item_group_code < 10){
            group_2_unbind();
            group_2_clear_forms();
        }
        closeSession();
        $('#group_one').show();
    });
    function chooseItemGroup(name){
        var res = resolveGroupCommonCode(name);
        item_group_code = res.code;
        if(item_group_code < 0) return;
        $('#group_two_text').text(menClothesPattern +' > ' + pattern);
        if(!$.isNumeric(item_group_code) || item_group_code < 0) console.log('some error');
        else if(item_group_code < 4) group_1_bind_init();
        else if(item_group_code < 10) group_2_bind_init();
        else if(item_group_code == 10) group_4_bind_init();
        else if(item_group_code == 11) group_5_bind_init();
        $('#section_2').show();
        $('#section_3').show();
        $('#section_4').show();
        /*group selection stuff*/
        $('#group_one').hide();
        $('#group_two').show();
    }

    /************************************************************/

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
        var tmp;
        if(cat == 'BOTTOM' || cat == 'WAIST') tmp = gr_1_eu_dropDown.parent().find('.dropdown-content');
        else if(cat == 'TOP') tmp = gr2_eu_dropDown.parent().find('.dropdown-content');
        else return;
        tmp.find('.lazy').remove();
        for(var i = 0; i < data.length; i++){
            var val = resolveLocale(data[i].name);
            tmp.append('<a class="lazy">'+val+'<input type="number" hidden value="'+data[i].id+'"></a>');
        }
        tmp.find('.lazy').click(function (){
            var t = $(this), dropbtn = tmp.parent().find('.dropbtn');
            dropbtn.text(t.text());
            dropbtn.append('<input type="number" hidden value="'+t.find('input').val()+'">');
            checkAllFormsGroup();
        })
    }
    function bindInputName(){
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
        if(item_group_code == 3) $('#example_group_11').show();
        else $('#example_group_1').show();
        unlock(add_size_button, false);
        bindInputName();
        origin_price_input.bind('input propertychange', function(){
            unlock(save_button, (checkForSaveButton()));
        });
        sale_price_input.bind('input propertychange', function(){
            unlock(save_button, (checkForSaveButton()));
        });
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
            var eu_waist_val = gr_1_eu_dropDown.text(),
                qty = addSizePreview(eu_waist_val),
                fields = [
                    {name: 'Waist', value: gr_1_waist.val()},
                    {name: 'Length', value: gr_1_length.val()},
                    {name: 'Bottom', value: gr_1_bottom.val()}
                ],
                size = {
                quantity: parseInt(qty), measurements: fields, eu_size: {id: gr_1_eu_dropDown.find('input').val()}};
            group_1_clear_forms();
            processNewSize(size, qty);
        });
    }
    function group_1_clear_forms (){
        gr_1_eu_dropDown.text(locale.label_choose);
        gr_1_waist.val('');
        gr_1_bottom.val('');
        gr_1_length.val('');
    }
    function group_1_unbind (){
        $('#item_group_1').hide();
        if(item_group_code == 3) $('#example_group_11').hide();
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
        if(item_group_code > 5 && item_group_code < 10) {
            $('#item_group_3').show();
            $('#example_group_3').show();
        }else $('#example_group_2').show();
        unlock(add_size_button, false);
        bindInputName();
        origin_price_input.bind('input propertychange', function(){
            unlock(save_button, (checkForSaveButton()));
        });
        sale_price_input.bind('input propertychange', function(){
            unlock(save_button, (checkForSaveButton()));
        });
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
        if(item_group_code > 5 && item_group_code < 10){
            gr_3_sleeve.bind('input propertychange', function(){
                checkAllFormsGroup();
                checkMeasurementInput(gr_3_sleeve);
            });
        }
        add_size_button.on('click', function(){
            var qty = addSizePreview(gr2_eu_dropDown.text()),
            fields = [
                {name: 'Shoulders', value: gr_2_shoulders.val()},
                {name: 'Length', value: gr_2_length.val()},
                {name: 'Chest', value: gr_2_chest.val()}
            ];
            if(item_group_code > 5) fields.push({name: 'Sleeve', value: gr_3_sleeve.val()});
            var size = {
                quantity: qty, measurements: fields, eu_size: {id: gr2_eu_dropDown.find('input').val()}
            };
            group_2_clear_forms();
            processNewSize(size, qty);
        });
    }
    function group_2_clear_forms (){
        gr_2_shoulders.val('');
        gr_2_chest.val('');
        gr_2_length.val('');
        if(item_group_code > 5 && item_group_code < 10) gr_3_sleeve.val('');
    }
    function group_2_unbind (){
        $('#item_group_2').hide();
        $('#example_group_2').hide();
        gr_2_shoulders.unbind();
        gr_2_length.unbind();
        gr_2_chest.unbind();
        if(item_group_code > 5 && item_group_code < 10){
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
    function group_4_bind_init (){
    }
    function group_5_bind_init (){
    }

    /*Common methods*/
    function checkAllFormsGroup (){
        var condition;
        if(item_group_code < 4) condition = conditionForAddSizeButtonGroup_1();
        else if(item_group_code < 6) condition = conditionForAddSizeButtonGroup_2();
        else if(item_group_code < 10) condition = conditionForAddSizeButtonGroup_3();
        unlock(add_size_button, condition);
        unlock(save_button, checkForSaveButton());
    }
    function checkForSaveButton (){
        return count_size > 0 && checkLength(origin_price_input) &&
               checkLength(sale_price_input) && checkLength(item_name_en) && checkLength(item_name_ru) &&
               checkLength(item_name_ua) && featuresOk;
    }
    function checkMeasurementInput (input){
        var value = input.val();
        if(value.length == 1 && value =='.' || value =='-') value = '';
        input.val(value.replace(/[^0-9.-]/g,''));
    }

    function checkLength (form){
        return form != undefined && !form.val().length <= 0
    }
    function unlock (button, enable){
        button.prop('disabled', !enable)
    }
    function quantityInputBind (){
        quantity_input.bind('input propertychange', function(){
            var val = quantity_input.val().replace(/[^0-9]/g,'');
            if(val < 1) val = 1;
            quantity_input.val(val);
        });
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
            case locale.group_accessories:     return {code: 11, item_group: en.group_accessories};
            case locale.group_beanies:         return {code: 12, item_group: en.group_beanies};
            default:                           return -1;
        }
    }
    function addSizePreview (eu){
        var qty = parseInt(quantity_input.val());
        quantity_input.val(1);
        $('#size_preview').append('<table class="icon-wrapper" id="table'+count+'">'+
            '<tr><td rowspan="4"><div class="size_icon">'+eu+'</div></td>'+
            '<tr><td class="td_del"><div class="del"></div></td></tr>'+
            '<tr><td><button class="size_quantity">x'+qty+'</button></td></tr></table></div>');
        return qty;
    }
    function processNewSize (size, qty){
        all_sizes.push(size);
        var selector = '#table' + count, table = $(selector),
            size_icon = table.find('.size_icon'),
            del_button = table.find('.del');
        table.mouseover(function(){
            del_button.show();
        });
        table.mouseleave(function(){
            del_button.hide();
        });
        var c = count;
        table.on('click', function(){
            console.log(c);
        });
        del_button.on('click', function(){
            $.when(table.fadeOut()).then(function(){
                setTimeout(function(){
                    table.remove()
                },500)
            });
            deleted.push(c);
            quantity_sizes -= all_sizes[c].quantity;
            count_size--;
            checkAllFormsGroup();
        });
        quantity_sizes += qty;
        count+=1;
        count_size+=1;
        checkAllFormsGroup();
    }
    function updateFinalSizes (){
        final_sizes = [];
        for(var i=0; i < count; i++){
            var rm = false;
            for(var k=0; k < deleted.length; k++){
                rm = (i== deleted[k]);
                if(rm) break;
            }
            if(!rm) {
                final_sizes.push(all_sizes[i]);
            }
        }
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
        updateFinalSizes();
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
            quantity: quantity_sizes,
            color: {id: $('#color_sample').find('input').val()},
            originPrice: parseInt(origin_price_input.val()*100),
            salePrice: parseInt(sale_price_input.val()*100),
            brand: {
                id: $('li#brand').find('.dropbtn').find('input').val()
            },
            size: final_sizes
        };
        console.log(JSON.stringify(item));
        $('#item').val(JSON.stringify(item));
        $('#sub').click();
    }
    /********************************************/
    save_button.on('click', function(){
        unlock(save_button, false);
        if(!$.isNumeric(item_group_code) || item_group_code < 0) console.log('some error');
        saveClothesItem();
    });
    function closeSession() {
        add_size_button.off('click');
        item_name_en.unbind();
        item_name_ru.unbind();
        item_name_ua.unbind();
        origin_price_input.unbind();
        sale_price_input.unbind();
        quantity_sizes = 0;
        quantity_input.val(1);
        origin_price_input.val(1);
        sale_price_input.val(1);
        count_size = 0;
        deleted = [];
        for(var i = 0; i < count; i++){
            deleted.push(i);
        }
    }
    quantityInputBind();

    /*******************image upload section**************/

    $('#filer_input').filer({
        showThumbs: true,
        limit: 8,
        appendTo: $('#upload_wrapper'),
        templates: {
            box: '<ul id="list" class="jFiler-items-list jFiler-items-grid"></ul>',
            item: '<li class="jFiler-item">\
                    <div class="jFiler-item-container">\
                        <div class="jFiler-item-inner">\
                            <div class="jFiler-item-thumb">\
                                <div class="jFiler-item-status"></div>\
                                <div class="jFiler-item-info">\
                                    <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                    <span class="jFiler-item-others">{{fi-size2}}</span>\
                                </div>\
                                {{fi-image}}\
                            </div>\
                            <div class="jFiler-item-assets jFiler-row">\
                                <ul class="list-inline pull-left"></ul>\
                                <ul class="list-inline pull-right">\
                                    <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                </ul>\
                            </div>\
                        </div>\
                    </div>\
                </li>',
            itemAppend: '<li class="jFiler-item">\
                        <div class="jFiler-item-container">\
                            <div class="jFiler-item-inner">\
                                <div class="jFiler-item-thumb">\
                                    <div class="jFiler-item-status"></div>\
                                    <div class="jFiler-item-info">\
                                        <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
                                        <span class="jFiler-item-others">{{fi-size2}}</span>\
                                    </div>\
                                    {{fi-image}}\
                                </div>\
                                <div class="jFiler-item-assets jFiler-row">\
                                    <ul class="list-inline pull-left">\
                                        <li><span class="jFiler-item-others">{{fi-icon}}</span></li>\
                                    </ul>\
                                    <ul class="list-inline pull-right">\
                                        <li><a class="icon-jfi-trash jFiler-item-trash-action"></a></li>\
                                    </ul>\
                                </div>\
                            </div>\
                        </div>\
                    </li>',
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
        addMore: true
    });

    /*Items group tree stuff*/
    function init(){
        $('.main-content .dropbtn').click(function(e){
            $(this).parent().find('.dropdown-content').toggle(150);
        });
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
                    drawCategoryPopup(treeData,$('#level-2').find('ul'),[3,4]);
                },50);
                fillColor(result.colorList);
                drawBrandsDropDown(result.brandList, $('li#brand').find('.dropdown-content'), result.brandImgUrl);
            },
            error: function(result){
                console.log('Error while getting itemsgroup =/');
                console.log(result)
            }
        });
    }

    function fillColor(data){
        var content = $('#color').find('.dropdown-content');
        for(var i=0; i<data.length; i++){
            var color = data[i].hex;
            content.append('<a class="color-option">'+resolveLocale(data[i].color)+'<div class="hex" style="background: '+color+'"><input hidden value="'+data[i].id+'"></div></a>')
        }
        $('.color-option').click(function(){
            var t = $(this), text = t.text(), hex = t.find('.hex');
            if(text.length > 15) text = text.substring(0,15) + '.';
            $('#color').find('.dropbtn').text(text);
            text =  $('#color_sample');
            text.css({'background':hex.css('background')});
            text.find('input').val(hex.find('input').val())
        });
    }
    $('#add-cat-popup').popup({
        openelement:'.changeGroup',
        closeelement:'.close_group',
        transition: 'all 0.3s'
    });
    $('#extraNotes_popup').popup({
        openelement:'.extra_note',
        closeelement:'.extra_close',
        transition: 'all 0.3s'
    });

    function drawBrandsDropDown(data, brandDropdown, imageUrl){
        for(var i=0; i< data.length; i++){
            brandDropdown.append('<a class="brand-option">' + data[i].name + '<input type="number" hidden value="' + data[i].id + '"></a>')
        }
        $('.brand-option').click(function(){
            var tmp = $(this);
            var dropbtn = tmp.parent().parent().find('.dropbtn');
            dropbtn.text(tmp.text());
            dropbtn.append('<input type="number" hidden value="'+tmp.find('input').val()+'">');
            $('#brand-logo').find('img').attr('src', magic+"../.." + imageUrl+tmp.text().toLowerCase().replace(/\s/g,"_")+'/logo.jpg').show();
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
            if(data[i].child.length > 0) obj.nodes = (createTreeData(data[i].child, parent +' > ' +obj.text));
            result.push(obj);
        }
        return result;
    }

    function resolveGender(text){
        if(text == 'Men' || text =='Мужское' || text == 'Чоловіче') gender = 0;
        else if(text == 'Women' || text == 'Женское' || text =='Жіноче') gender = 1;
    }

    function drawCategoryPopup(data, lvl, id){
        for(var i=0; i< data.length; i++){
            var child = '<input hidden type="number" value="'+i+'">';
            if(data[i].nodes != undefined && data[i].nodes.length > 0) child= child+'<span class="glyphicon glyphicon-menu-right"></span>';
            lvl.append('<li id="lvl-'+data[i].level+i+'">'+data[i].text+child+'</li>');
            child = '#lvl-'+data[i].level+i;
            $(child).click(function(){
                var li = $(this);
                li.parent().find('.chosen').removeClass('chosen');
                var number = li.find('input').val();
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
                drawCategoryPopup(data[number].nodes, levels[data[number].level+1].level_div.find('ul'));
            })
        }
    }
    $('#accept_group').click(function(){
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
    function removeRowClick(button){
        button.click(function(){
            var list = $(this).parent().parent();
            list.fadeOut(150);
            $.when(function(){
                setTimeout(function(){
                    list.remove();
                }, 200);
            }).then(function(){
                checkAllFeaturesInputs($('input.val'));

            });
        })
    }

    function checkAllFeaturesInputs(input){
        var cond = true;
        input.each(function(){
            if($(this).val().length < 1) cond = false;
        });
        featuresOk = cond;
        unlock(save_button, checkForSaveButton());
    }

    function checkFeatureInputLength(input){
        featuresOk = true;
        input.each(function(){
            $(this).bind('input propertychange', function(){
                var currentInput = $(this), cond = currentInput.val().length > 0;
                if(cond) currentInput.removeClass('broken');
                else currentInput.addClass('broken');
                checkAllFeaturesInputs(input);
            })
        });
    }

    $('button.feature').click(function(){
        var ul = $('.item_description'),
            holder = ul.parent().find('.feature_holder'),
            lastEq = holder.find('ul').length;
        holder.append('<ul class="list-inline feature_preview">' +
            '<li><input type="text" class="height val f-en" value="'+removeVal(ul.find('.en'))+'" spellcheck="false"></li>' +
            '<li class="marg"><input type="text" class="height val f-ru" value="'+removeVal(ul.find('.ru'))+'" spellcheck="false"></li>' +
            '<li class="marg"><input type="text" class="height val f-ua" value="'+removeVal(ul.find('.ua'))+'" spellcheck="false"></li>' +
            '<li><button class="remove_row"></button></li></ul>');
        ul.parent().find('button.feature').prop('disabled', true);
        checkFeatureInputLength(holder.find('input.val'));
        ul = holder.find('ul:eq('+lastEq+')');
        removeRowClick(ul.find('button.remove_row'));
    });
    $('.accept-button').click(function(){
        x_buff = $('#ex-info').val();
    });
    $('#extraNotes_popup').find('.cancel-button').click(function(){
        $('#ex-info').val(x_buff);
    });
    init();
});
/*]]>*/