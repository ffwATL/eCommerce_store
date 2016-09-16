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

    $('.tabs').tabslet();
    sizeBlock.find('.dropbtn').click(function(){
        $(this).parent().find('.dropdown-content').toggle();
    });


    function getSize(name){
        return sizeMap[name]
    }
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
                console.log(result);
            },
            error: function(result){
                console.log(result)
            }
        });
    }

    function processData(data){
        var totalQTY = $('#totalQTY');
        $('.item_title').text(data.itemName);
        totalQTY.find('.qty').text(data.quantity);
        $('span.originPrice').text(data.originPrice/100);
        $('span.salePrice').text(data.salePrice/100);
        if(data.active)  {
            totalQTY.removeClass('itemInactive');
            totalQTY.addClass('itemActive');
        }
        var size_ul= $('.size_block').find('.sizeSelect_ul');
        var sizeBlock = size_ul.find('.dropdown-content');
        var sizeQty = size_ul.find('.size_qty');
        for(var i = 0; i< data.size.length; i++){
            var size = resolveLocale(data.size[i].eu_size.name);
            sizeMap[size] = {name: size, qty: data.size[i].quantity, id: data.size[i].id,
                measurements: [
                        {name:data.size[i].measurements[0].name, value: data.size[i].measurements[0].value},
                        {name:data.size[i].measurements[1].name, value: data.size[i].measurements[1].value},
                        {name:data.size[i].measurements[2].name, value: data.size[i].measurements[2].value}
                ]
            };
            sizeBlock.append('<a><button value="'+size+'">'+size+'</button><span class="qty">In stock: '+data.size[i].quantity+'</span></a>');
        }
        $('#color_hex').css('background', data.color.hex);
        $('#color_name').text('('+resolveLocale(data.color.color)+')');
        var code = resolveGroupCommonCode(resolveLocale(data.itemGroup.groupName));

        sizeBlock.find('a').click(function(){
            var val = $(this).find('button').val();
            processMeasurements(code.code, sizeMap[val]);
            size_ul.find('.dropbtn').text(val);
            /*sizeQty.text(sizeMap[val].qty)*/

        });
    }

    function processMeasurements(code, data){
        console.log(code);
        console.log(data);
        var ms = $('#measurement');
        ms.find('ul').remove();
        if(code < 4){
            ms.append('<ul class="list-inline"><li class="first"><label>'+locale.label_waist+'</label>' +
                '<input type="text" readonly value="'+data.measurements[0].value+'"></li>' +
                '<li class="second"><label>'+locale.label_length+'</label>' +
                '<input type="text" readonly value="'+data.measurements[1].value+'"></li>' +
                '<li class="third"><label>'+locale.label_bottom+'</label>' +
                '<input type="text" readonly value="'+data.measurements[2].value+'"></li></ul>')
        }
    }

    function resolveGroupCommonCode (name){
        pattern = name.trim();
        var en = locale;
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
});
/*]]>*/