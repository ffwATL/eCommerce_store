/*<![CDATA[*/
$(function(){
    var magic =/*[[@{context:}]]*/'';
    var sizeMap = {};
    var sizeBlock = $('.size_block');
    var lang = $.cookie("app");
    if(lang == undefined || lang.length < 2) lang = 'en';
    var locale = getLocale(lang);
    var csrf = {
        token : $("meta[name='_csrf']").attr("content"),
        header : $("meta[name='_csrf_header']").attr("content")
    };
    $('.lang .dropdown-content a').each(function(){
        $(this).attr('href',this.href+'&id='+$('#itemId').val())
    });

    /*Initialization of tabs plugin*/
    $('.tabs').tabslet();
    sizeBlock.find('.dropbtn').click(function(){
        $(this).parent().find('.dropdown-content').toggle();
    });

    /*Getting item information by requested itemId and managing the whole process on the page*/
    function refreshData(){
        $.ajax({
            url: magic + "../../manage/ajax/get/item/single",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            data: {'id':$('#itemId').val()},
            success: function(result){
                processData(result);
                $('.sp-wrap').smoothproducts();
                $('.sp-thumbs a')[0].click();
                console.log(result);
            },
            error: function(result){
                console.log(result)
            }
        });
    }
    /*Filling up size map and drawing size drop-down elements*/
    function sizeBlockRoutine(data, code){
        var sizeContent = sizeBlock.find('.dropdown-content');
        for(var i = 0; i< data.size.length; i++){
            var size = resolveLocale(data.size[i].eu_size.name);
            sizeMap[size] = {name: size, qty: data.size[i].quantity, id: data.size[i].id, measurements: []};
            if(code < 10) sizeMap[size].measurements.push(
                {name:data.size[i].measurements[0].name, value: data.size[i].measurements[0].value},
                {name:data.size[i].measurements[1].name, value: data.size[i].measurements[1].value},
                {name:data.size[i].measurements[2].name, value: data.size[i].measurements[2].value});
            if(code > 5 && code < 10) sizeMap[size].measurements.push({name:data.size[i].measurements[3].name, value: data.size[i].measurements[3].value});
            sizeContent.append('<a><button value="'+size+'">'+size+'</button><span class="qty">In stock: '+data.size[i].quantity+'</span></a>');
        }
    }
    /*Adding an event to the dropdown-content element*/
    function sizeBlockOnClick(size_ul, sizeQty, code){
        var k = false;
        size_ul.find('.dropdown-content a').click(function(){
            if(!k){
                $('#inStock').fadeIn(100);
                k = true;
            }
            var val = $(this).find('button').val();
            processMeasurements(code, sizeMap[val]);
            size_ul.find('.dropbtn').text(val);
            sizeQty.text(sizeMap[val].qty)
        })
    }

    function resolveItemDescription(desc){
        console.log(desc);
        var arr = desc.split('|'), ul = $('#itemDescription');
        $('.f-li').remove();
        for(var i = 0; i<arr.length; i++){
            if(arr[i].length>0) ul.append('<li class="f-li">'+arr[i]+'</li>');
        }
    }

    /*Processing item data from ajax request*/
    function processData(data){
        $('#totalQTY').text(data.quantity);
        document.title = resolveLocale(data.itemName);
        $('.item_title').text(document.title);
        $('#originPrice').text(data.originPrice/100);
        var salePrice =data.salePrice/100,
            code = resolveGroupCommonCode(resolveLocale(data.itemGroup.groupName)).code,
            sp = $('.sp-wrap');
        if(data.discount > 0) {
            $('.sale').css('color','#cc6e81');
            $('span.prev_price').text(salePrice);
            $('.prev_price').css('display','inline');
        }
        $('span.salePrice').text(Math.round(salePrice-(salePrice*data.discount/100)).toFixed(0));
        $('.switch-active input').prop('checked', data.active);
        $('#discount').text(data.discount);
        $('#addedBy').text(data.addedBy.email);
        $('#color_hex').css('background', data.color.hex);
        $('#color_name').text('('+resolveLocale(data.color.color)+')');
        $('#tab-2').find('p.info').text(data.brand.name);
        $('#brand_logo').find('img').attr('src',magic+'../..'+data.brandImgUrl+data.brand.name.toLowerCase().replace(/\s/g,"_")+'/logo.jpg');
        $('p.aboutBrand').text(resolveLocale(data.brand.description));
        console.log(data.extraNotes);
        $('#tab-3').find('p').text(data.extraNotes);
        $('.productId li:eq(1)').text(data.id);
        resolveItemDescription(resolveLocale(data.description));
        sizeBlockRoutine(data, code);
        sizeBlockOnClick($('.size_block').find('.sizeSelect_ul'),$('#inStock'), code);
        for(i = 0; i<data.images.length; i++){
            var url = magic+'../..'+data.images[i];
            sp.append('<a href="'+url+'"><img src="'+url+'"></a>')
        }
    }

    function processMeasurements(code, data){
        var ms = $('#measurements');
        ms.show();
        ms = ms.find('#gen_measurements');
        ms.find('ul').remove();
        if(code < 4){
            ms.append('<ul class="list-inline"><li class="first"><label>'+locale.label_waist+'</label>' +
                '<input type="text" readonly value="'+data.measurements[0].value+'"></li>' +
                '<li class="second"><label>'+locale.label_length+'</label>' +
                '<input type="text" readonly value="'+data.measurements[1].value+'"></li>' +
                '<li class="third"><label>'+locale.label_bottom+'</label>' +
                '<input type="text" readonly value="'+data.measurements[2].value+'"></li></ul>')
        }else if(code < 10){
            ms.append('<ul class="list-inline"><li class="first"><label>'+locale.label_shoulders+'</label>' +
                '<input type="text" readonly value="'+data.measurements[0].value+'"></li>' +
                '<li class="second"><label>'+locale.label_length+'</label>' +
                '<input type="text" readonly value="'+data.measurements[1].value+'"></li>' +
                '<li class="third"><label>'+locale.label_chest+'</label>' +
                '<input type="text" readonly value="'+data.measurements[2].value+'"></li></ul>');
            if(code > 5){
                var top = $('#clothes_top');
                top.find('ul').remove();
                top.append('<ul class="list-inline"><li class="fourth"><label>'+locale.label_sleeve+'</label>' +
                    '<input type="text" readonly value="'+data.measurements[3].value+'"></li></ul>')
            }
        }else if(code == 10){
            ms.append('<ul class="list-inline"><li class="first"><label>'+locale.label_insole+'</label>' +
                '<input type="text" readonly value="'+data.measurements[0].value+'"></li></ul>');
        }
    }

    function resolveGroupCommonCode (name){
        var pattern = name.trim(), en = locale;
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
    refreshData();
    /*$('.sp-wrap').smoothproducts();
    $('.sp-thumbs a')[0].click();*/
});
/*]]>*/