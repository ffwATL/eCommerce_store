/*<![CDATA[*/
$(function(){
    var lang = $.cookie("app");
    if(lang == undefined || lang.length < 2) lang = 'en';
    var editClass = $('#edit_popup');
    var locale = getLocale(lang);
    var magic =/*[[@{context:}]]*/'';
    var pgnt = $('#pgnt1');
    var minPriceSale=0, maxPriceSale=1000;
    var filterDiv = $('.filter-block0');
    var blockWrapper = $('.block-wrapper');
    var sidebar = $('#sidebar');
    var filterGlobal =  $('#filter-global0');
    var firstFilter = false;
    var tabs = $('.tabs');
    var allTab = $('.all');
    var inactiveTab = $('.inactive');
    var isActiveTab = $('.isActive');
    var saleTab = $('.sale');
    var currentTab = allTab;
    var currentItem;
    var filter_list = $('.filter-list');
    var info ={info_h2: $('.info_h2'), info_h5: $('.info_h5')};
    var sliders=[
        {
            sliderSale:$('#slider_tab0_1'),
            sliderOrigin: $('#slider_tab0_2')
        }
    ];
    var tabsLi = tabs.find('#forTabs').find('li');
    var activeTab = 0;
    var allGridDiv = $('#grid-all');
    var inactiveGridDiv = $('#grid-inactive');
    var isActiveGridDiv = $('#grid-isActive');
    var saleGridDiv = $('#grid-sale');
    var itemGridDiv = allGridDiv;
    var identifiers=[];
    var staticFilter ={
        refineFilter: {itemGroup:[], color: [], gender: [], brand:[], size:[], cat:''},
        displayAsList: true,
        updates: {id:[],expressInfo:{}},
        filter: {pge: 0, pgeSize: 8, totalItems: 0, isActive: '', isSale: '', salePrice:'', originPrice:''}
    };
    var tempItemOpt ={
        itemGroupId: undefined,
        itemGroupCat: undefined,
        colorId: undefined,
        colorHex: undefined
    };
    var notFound = false;
    var filter = staticFilter.filter; /*default filter settings*/
    var firstTabFilter = $('.firstTabFilter');
    var secondTabFilter = $('.secondTabFilter');
    var thirdTabFilter = $('.thirdTabFilter');
    var fourthTabFilter = $('.fourthTabFilter');
    var csrf = {
        token : $("meta[name='_csrf']").attr("content"),
        header : $("meta[name='_csrf_header']").attr("content")
    };
    var inactive = false;
    var onSale = false;
    var isActive = false;
    var globalEdit = $('#global_edit_popup');

    tabs.tabslet();

    function tabClick (active, grid, tab, h2, h5){
        staticFilter.filter.pge = 0;
        var routine = function(){
            activeTab = active;
            itemGridDiv = grid;
            info.info_h2.text(h2);
            info.info_h5.text(h5);
            $('.notFound').remove();
            notFound = false;
            var selector = '#pgnt' + (activeTab + 1);
            pgnt = $(selector);
            currentTab= tab;
            return {pgnt: pgnt};
        };
        $.when(routine()).then(function (result){
            refreshItems();
        });
    }

    function initActionPanel (tab){
        $('.action-panel').find('.dropbtn').click(function(e){
            $(this).parent().find('.dropdown-content').toggle(150);
        });
    }

    $('#all').click(function(){
        staticFilter.filter.isActive = '';
        staticFilter.filter.isSale = '';
        tabClick(0, allGridDiv, allTab, locale.grid_tabs_info_all_h2, locale.grid_tabs_info_all_h5);
        displayLeftSide();
    });

    $('#inactive').click(function(){
        staticFilter.filter.isActive = false;
        staticFilter.filter.isSale = '';
        tabClick(1, inactiveGridDiv, inactiveTab, locale.grid_tabs_info_inactive_h2, locale.grid_tabs_info_inactive_h5);
        if(!inactive){
            activateLeftSide();
           /* initActionPanel(inactiveTab);*/
            init(currentTab.find('select.sort-by'));
            inactive = true;
        } else displayLeftSide();
    });

    $('#isActive').click(function () {
        staticFilter.filter.isActive = true;
        staticFilter.filter.isSale = '';
        tabClick(2, isActiveGridDiv, isActiveTab,locale.grid_tabs_info_active_h2, locale.grid_tabs_info_active_h5);
        if(!isActive){
            activateLeftSide();
           /* initActionPanel(isActiveTab);*/
            init(currentTab.find('select.sort-by'));
            isActive = true;
        } else displayLeftSide();
    });

    $('#onSale').click(function(){
        staticFilter.filter.isActive = true;
        staticFilter.filter.isSale = true;
        tabClick(3, saleGridDiv, saleTab, locale.grid_tabs_info_sale_h2, locale.grid_tabs_info_issale_h5);
        if(!onSale){
            activateLeftSide();
           /* initActionPanel(saleTab);*/
            init(currentTab.find('select.sort-by'));
            onSale = true;
        }else displayLeftSide();
    });

    /**
     * Before send filtering information it should be converted to useful format,
     * this format for now is "2|3|6|13|8". Then this info attached to the 'filter'
     * object. Algorithm depends on input parameter 'cat';
     * @param cat item main category name (level 1).
     */
    function fillTempFilter (cat){
        if(cat == 'Clothes' || cat == 'Одежда' || cat == 'Одяг'){
            filter.itemGroup = stringtify(staticFilter.refineFilter.itemGroup);
            filter.brand = stringtify(staticFilter.refineFilter.brand);
            filter.size = stringtify(staticFilter.refineFilter.size);
            filter.gender = stringtify(staticFilter.refineFilter.gender);
        }
        if(staticFilter.filter.isSale != '') filter.isSale = staticFilter.filter.isSale;
        if(staticFilter.filter.isActive != '') filter.isActive = staticFilter.filter.isActive;
        if(staticFilter.filter.salePrice != '') filter.salePrice = staticFilter.filter.salePrice;
        if(staticFilter.filter.originPrice != '') filter.originPrice = staticFilter.filter.originPrice;
        if(staticFilter.refineFilter.color.length > 0) filter.color = stringtify(staticFilter.refineFilter.color);
        else filter.color = undefined;
        filter.pge =  staticFilter.filter.pge;
        filter.pgeSize = staticFilter.filter.pgeSize;
        filter.cat = cat;

    }

    /******Method for refreshing items and drawing grid******/
    function refreshItems (){
        onCheckBoxSelected(false);
        fillTempFilter(staticFilter.refineFilter.cat);
        $.ajax({
            url: magic + "../../manage/ajax/get/item/all",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            data: filter,
            success: function(result){
                identifiers=[];
                $('.item').remove();
                $('.pg').remove();
                $('.next').remove();
                $('.previous').remove();
                drawItems(result, pgnt);
            },
            error: function(result){
                console.log(result)
            }
        });
    }


    function drawGrid (arr, b){
        for(var i=0; i<arr.length; i++){
            var item = arr[i];
            b.append('<li class="item"><a href="item?id='+item.id+'"><div class="inner"><div class="thumbnail">' +
                '<img src="'+magic+'../..'+item.thumbnailUrl+'"></div><div class="item-name"><a>'+item.itemName+'</a></div>' +
                '<div class="origin-price"><span class="price">'+item.originPrice/100+ '</span>' +
                '<span class="currency"> '+item.currency+'</span></div>' +
                '<div class="sale-price"><span class="price">'+item.salePrice/100+ '</span>' +
                '<span class="currency"> '+item.currency+'</span></div></div></a></li>')
        }
        if(arr.length == 0 && !notFound){
            b.append('<li class="notFound"><img class="notFoundImg" src="'+magic+'../../resources/img/sad.jpg">'+locale.no_results_found+'</li>');
            notFound = true;
        }else if(arr.length > 0 && notFound) {
            $('.notFound').remove();
            notFound = false;
        }
    }

    function activateLeftSide(){
        setTimeout(function(){
            currentTab.find('.main-check').change(function(){
                displayLeftSide(this.checked, true);
            });
        }, 100);
    }
    function displayLeftSide(display, main, leftSide){
        if(leftSide == undefined) leftSide = currentTab.find('.left-side');
        if((display == undefined) && (main == undefined)) leftSide.hide();
        else if((display && main) || (staticFilter.updates.id.length > 0))  leftSide.fadeIn(200);
        else leftSide.fadeOut(100);
    }

    function checkInputNumberField (input, min, max){
        var val = input.val();
        if(val < min) input.val(min);
        if(max != undefined && val > max) input.val(max);
    }

    function checkInputTextField (input){
        if(input.val().length < 5){
            input.css({'border-bottom':'solid 4px #F25D75', background: '#FCE8EC', color: '#cc6e81'});
            return false;
        }else {
            input.css({'border-bottom':'solid 4px #e7e7e7', background: 'white', color: '#727272'});
        }
        return true;
    }

    function onCheckBoxSelected (condition){
        if(!condition) staticFilter.updates.id = [];
        else staticFilter.updates.id = identifiers;
        $('.check').prop('checked', condition);
        $('.main-check').prop('checked', condition);
    }

    function drawList (arr, b){
        console.log(arr);
        for(var i=0; i<arr.length; i++){
            var item = arr[i];
            var checked = '';
            var c="";
            var f='';
            var cl='';
            var finalPrice = Math.round(((item.salePrice/100) - (item.salePrice/100)*item.discount/100)).toFixed(1);
            if(item.discount > 0) {
                c ='<div class="discount-logo"><span class="status"><img src="'+magic+'../../resources/img/offer.png"></span>' +
                    '<span class="last">'+(0-item.discount)+'<span>%</span></span></div>';
                f='<div class="final"><span class="price">'+finalPrice+'</span><span class="currency"> '+item.currency+'</span></div>'
                cl='line-through';
            }
            if(item.active) checked = 'checked';
            b.append('<tr class="item"><td><input class="check" type="checkbox"></td><td class="id">'+item.id+'</td>' +
                '<td><div class="thumb wrapper"><img class="icon" src="'+magic+'../..'+item.thumbnailUrl+'">' + c+
                '<input hidden class="color" type="text" value="'+resolveLocale(item.color.color)+'">' +
                '<input hidden class="color-id" type="number" value="'+item.color.id+'">'+
                '<input hidden class="color-hex" type="text" value="'+item.color.hex+'">'+
        '<input hidden class="item-group-val" type="text" value="'+resolveLocale(item.itemGroup.groupName)+'"> ' +
        '<input hidden class="item-group-id" type="number" value="'+item.itemGroup.id+'">' +
        '<input hidden class="item-group-cat" value="'+item.itemGroup.cat+'"> </div></td>' +
        '<td class="item-name"><a href="item?id='+item.id+'">'+item.itemName+'</a></td><td><span class="count">'+item.quantity+'</span></td>' +
        '<td><label class="switch-active"><input type="checkbox" '+checked+'><div class="slider"></div></label></td>' +
        '<td  class="date"><span class="import-date">'+format(new Date(item.importDate).toLocaleString(),'dd/MM/yyyy')+'</span></td>' +
        '<td><div class="origin-price"><span class="price">'+item.originPrice/100+'</span><span class="currency"> '+item.currency+'</span></div></td>' +
        '<td><div class="sale-price"><div class="sale '+cl+'"><span class="price">'+item.salePrice/100+'</span><span class="currency"> '+item.currency+'</span></div>' +
        f+'</div>' +
        '<td class="dropdown1"><button class="dropbtn">'+locale.options+'</button><div class="dropdown-content"><a class="expressEdit" href="#">'+locale.expressEdit+'</a>' +
        '<a href="#">'+locale.add_to_group+'</a></div></td></tr>')
    }
    initTableDropdown();
    if(arr.length == 0 && !notFound){
        b.append('<tr class="notFound"><td colspan="10" ><img class="notFoundImg" src="'+magic+'../../resources/img/sad.jpg">'+locale.no_results_found+'</td></tr>');
        notFound = true;
    }else if(arr.length > 0 && notFound) {
        $('.notFound').remove();
        notFound = false;
    }
}

/*******Method for drawing items grid*****/
function drawItems (result, paginationDiv){
    staticFilter.items = result.items;
    if(!staticFilter.displayAsList) drawGrid(staticFilter.items, itemGridDiv);
    else drawList(staticFilter.items, currentTab.find('table.list-items'));
        var pge = parseInt(result.pge);
        var totalPages = result.totalPages;
        staticFilter.filter.totalItems = result.totalItems;
        resolvePagination(pge, totalPages, paginationDiv);
        $('.next').click(function(){
            staticFilter.filter.pge=pge+1;
            refreshItems();
        });
        $('.previous').click(function(){
            staticFilter.filter.pge=pge-1;
            refreshItems();
        });
        $('.pg').each(function(){
            var t = $(this);
            t.on('click', function(){
                staticFilter.filter.pge= parseInt(t.text()) -1;
                refreshItems();
            })
        });
        resolveI18n();
    }

    /*****Pagination section**************/
    function addLastPage (currentPage, totalPages, paginationDiv){
        if(currentPage + 2 < (totalPages - 1)) paginationDiv.append('<span class="pg"> ... </span>');
        if((totalPages-1) == currentPage) paginationDiv.append('<a class="pg"><button class="active">'+totalPages+'</button></a>');
        else paginationDiv.append('<a class="pg" href="#main"><button>'+(totalPages)+'</button></a>');
    }

    function addNext (currentPage, totalPages, paginationDiv){
        if(currentPage < (totalPages-1)){
            var nxt = locale.grid_pagination_next;
            paginationDiv.append('<a class="next" href="#main"><button>'+nxt+'</button></a>');
        }
    }

    function addPrevious (currentPage, paginationDiv){
        var prvs = locale.grid_pagination_prev;
        paginationDiv.append('<a class="previous" href="#main"><button>'+prvs+'</button></a>');
    }

    function addFirstPage (paginationDiv, pgeSize){
        paginationDiv.append('<a class="pg" href="#main"><button>1</button></a>');
    }

    function firstCase (currentPage, totalPages, paginationDiv){
        for(var i = 0; i < totalPages; i++){
            if(i == currentPage) paginationDiv.append('<a class="pg"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a class="pg" href="#main"><button>'+(i+1)+'</button></a>');
        }
        addNext(currentPage, totalPages, paginationDiv);
    }

    function secondCase (currentPage, totalPages, paginationDiv){
        for(var i = 0; i < 5; i++){
            if(i == currentPage) paginationDiv.append('<a class="pg" href="#main"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a class="pg" href="#main"><button>'+(i+1)+'</button></a>');
        }
        paginationDiv.append('<span class="pg"> ... </span><a class="pg" href="#main"><button>'+totalPages+'</button></a>');
        addNext(currentPage, totalPages, paginationDiv);
    }

    function thirdCase (currentPage, totalPages, paginationDiv){
        addPrevious(currentPage, paginationDiv);
        addFirstPage(paginationDiv);
        paginationDiv.append('<span class="pg"> ... </span>');
        for(var i= currentPage-2; i< (totalPages -1) && i <= (currentPage+2); i++){
            if(i == currentPage) paginationDiv.append('<a class="pg" href="#main"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a class="pg" href="#main"><button>'+(i+1)+'</button></a>');
        }
        addLastPage(currentPage, totalPages, paginationDiv);
        if(currentPage != totalPages - 1) {
            addNext(currentPage, totalPages, paginationDiv);
        }
    }

    function resolvePagination  (currentPage, totalPages, paginationDiv) {
        if(totalPages == 1) return 0;
        else if(totalPages <= 5){
            if(currentPage > 0) addPrevious(currentPage, paginationDiv);
            firstCase(currentPage, totalPages, paginationDiv);
        }
        else if(totalPages > 6 && currentPage <= 4){
            if(currentPage > 0) addPrevious(currentPage, paginationDiv);
            secondCase(currentPage, totalPages, paginationDiv);
        }
        else if(totalPages > 6 && currentPage > 4) thirdCase(currentPage, totalPages, paginationDiv);
    }
    /***************Pagination END**************/

    function stringtify (arr){
        if(arr != undefined && arr.length > 0){
            var res = "";
            for(var i = 0; i<arr.length; i++){
                if(i == 0) res = arr[i];
                else res = res + "|"+arr[i];
            }
            return res;
        }else return '';
    }

    refreshItems();

    function refineByPrice (data, sale){
        if(sale) staticFilter.filter.salePrice=data.from*100+'|'+data.to*100;
        else staticFilter.filter.originPrice =data.from*100+'|'+data.to*100;
        staticFilter.filter.pge=0;
        refreshItems();
    }

    function sliderInit (){
        info.info_h2.text(locale.grid_tabs_info_all_h2);
        info.info_h5.text(locale.grid_tabs_info_all_h5);
        for(var i=0; i<sliders.length; i++){
            sliders[i].sliderSale.ionRangeSlider({
                type: "double",
                grid: true,
                min: 0,
                max: 1000,
                from: minPriceSale,
                to: maxPriceSale,
                onFinish: function (data) {
                    refineByPrice(data, true)
                },
                step: 50
            });
            sliders[i].sliderOrigin.ionRangeSlider({
                type: "double",
                grid: true,
                min: 0,
                max: 1000,
                from: minPriceSale,
                to: maxPriceSale,
                onFinish: function (data) {
                    refineByPrice(data, false)
                },
                step: 50
            });
        }
        changeSideBarHeight(sidebar);
    }

    sidebar.css('height',sidebar.height() + 30);
    function makeSticky (){
        activateLeftSide(); //FIXME: move it from here for heavens sake please
        initActionPanel(allTab);
        setTimeout(function(){
            $(function() {
                return $("#sidebar").stick_in_parent({
                    parent: $('.content'),
                    spacer: false
                });
            });
        }, 500);
    }

    makeSticky();
    blockWrapper.css('min-height', sidebar.height());

    function hideGlobalFilter (){
        var ul = filterGlobal.find('ul');
        if(!firstFilter) setTimeout(function(){filterGlobal.addClass('left-line');},100);
        else filterGlobal.removeClass('left-line');
        firstFilter = !firstFilter;
        changeSideBarHeight(sidebar);
        ul.toggle(100);
    }

    filterGlobal.find('h5').click(function(){
        hideGlobalFilter();
    });

    function changeSideBarHeight (sidebar){
        sidebar.css('height', 'auto');
        setTimeout(function(){
            var main = $('.main');
            main.css('min-height', sidebar.height()+10);
            main.css('height','auto');
        },100);
        setTimeout(function(){
            $(document.body).trigger("sticky_kit:recalc");
        },150);
    }

    $('.sort-bar').find('ul').find('a').click(function(){
        if (staticFilter.filter.pgeSize == 4) staticFilter.filter.pgeSize = staticFilter.filter.pgeSize*2;
        else staticFilter.filter.pgeSize = staticFilter.filter.pgeSize/2;
        staticFilter.filter.pge=0;
        refreshItems()
    });

    function linkCheckedGlobalFilter (panelFilter){
        var parent = panelFilter.parent();
        var name = parent.find('label').text().trim();
        var listSelector = name.replace(new RegExp(escapeRegExp(' '), 'g'),'');
        panelFilter.change(function(){
            if(this.checked){
                var transl = lang != 'en' ? transliterate(listSelector) : listSelector;
                console.log(transl);
                filter_list.append('<li class="checked filter '+transl+'">'+name+'<button></button></li>');
                linkFilterListToFilterPanel('.'+transl, panelFilter,undefined,undefined)
            }
            globalFilterRoutine(name, this.checked, parent,'.'+transl);
            staticFilter.filter.pge = 0;
            changeSideBarHeight(sidebar);
        });
    }

    function globalFilterRoutine (cat, checked, parent,listSelector){
        staticFilter.refineFilter.cat = cat;
        if(cat == 'Clothes' || cat =='Одежда'|| cat=='Одяг'){
            if(checked){
                $(listSelector).addClass('clothes');
                getClothesFilters();
                setTimeout(function(){
                    refreshItems();
                }, 100);
            }else{
                var selector = '#filter-clothes';
                $(selector).remove();
                selector = '.filter';
                var clth = $(selector);
                clth.fadeOut(500);
                parent.removeClass('checked');
                staticFilter.refineFilter.cat = "";
                staticFilter.refineFilter.brand=[];
                staticFilter.refineFilter.size=[];
                staticFilter.refineFilter.itemGroup = [];
                staticFilter.refineFilter.color = [];
                staticFilter.refineFilter.gender=[];
                setTimeout(function(){
                    clth.remove();
                    refreshItems()
                }, 650);
            }
        }
    }

    function linkFilterListToFilterPanel (listSelector, panelFilter, name, array){
        var listElement = $(listSelector);
        listElement.show();
        listElement.find('button').click(function(){
            var global = (name == undefined && array == undefined);
            panelFilter.prop('checked', false);
            listElement.fadeOut(500);
            staticFilter.filter.pge=0;
            if(global) panelFilter.trigger('change');
            else{
                removeFromArray(name, array);
                setTimeout(function(){
                    listElement.remove();
                    refreshItems()
                },650)
            }
        });
    }

    function addGenderFilter (r){
        filterDiv.append('<div id="filter-clothes"><hr></div>');
        r = r.gender;
        var selector = '#filter-clothes';
        $(selector).append('<div id="filter-gender" class="line"><h5>'+locale.grid_filter_gender+'</h5>' +
            '<ul class="list-inline twoRows"></ul></div></div>');
        selector = '#filter-gender';
        var filterGender = $(selector);
        var ul =filterGender.find('ul');
        for(var i=0; i< r.length; i++){
            var name = resolveLocale(r[i].groupName);
            ul.append('<li><span>' +
                '<input type="checkbox" class="gender_filter"><label>'+name+'</label>' +
                '<input hidden class="id" value="'+resolveGender(name)+'"></span></li>')
        }
        addToggleToH5(filterGender,false, ul);
        selector = '.gender_filter';
        linkCheckedFilter(selector, staticFilter.refineFilter.gender,'clothes');
    }

    /**
     * Method for adding 'category' filter options;
     * @param r result response object from server side.
     */
    function addClothesCategoryFilter (r){
        r = r.usedCat;
        console.log(r);
        var filterClothes = filterDiv.find('#filter-clothes');
        filterClothes.append('<hr><div class="category" id="category">' +
            '<h5>'+locale.grid_filter_category+'</h5><ul class="list-unstyled"></ul></div>');
        filterClothes = filterClothes.find('.category').find('ul');
        for(var i = 0; i< r.length; i++){
            filterClothes.append('<li><span>' +
                '<input type="checkbox" class="cat_filter line"><label>'+resolveLocale(r[i].groupName)+'</label>' +
                '<input hidden class="id" value="'+r[i].id+'"></span></li>')
        }
        addToggleToH5(filterClothes.parent(), false, filterClothes);
        var selector = '.cat_filter';
        linkCheckedFilter(selector, staticFilter.refineFilter.itemGroup,'clothes');
    }

    function addClothesBrandFilter (r){
        var list = r.brandList;
        var selector = '#filter-clothes';
        $(selector).append('<hr><div id="filter-brand" class="line"><h5>'+locale.grid_filter_brand+'</h5>' +
            '<ul class="list-inline twoRows"></ul></div></div>');
        selector = '#filter-brand';
        var filterBrand = $(selector);
        var ul =filterBrand.find('ul');
        for(var i=0; i< list.length; i++){
            ul.append('<li><span><input type="checkbox" class="brand_filter"><label>'+list[i].name+'</label>' +
                '<input hidden class="id" value="'+list[i].id+'"></span></li>')
        }
        addToggleToH5(filterBrand,false, ul);
        selector = '.brand_filter';
        linkCheckedFilter(selector, staticFilter.refineFilter.brand,'clothes');
    }

    function addClothesSizeFilter (r){
        var selector = '#filter-clothes';
        $(selector).append(('<hr><div id="size_filter" class="line"><h5>'+locale.grid_filter_size+'</h5>' +
        '<ul class="list-inline twoRows"></ul></div></div>'));
        selector = '#size_filter';
        var sizeFilter = $(selector);
        var ul = sizeFilter.find('ul');
        var s = r.size;
        for(var i=0; i< s.length; i++){
            ul.append('<li><span><input type="checkbox" class="clth_size_filter"><label>'+resolveLocale(s[i].name)+'</label>' +
                '<input hidden class="id" value="'+s[i].id+'"></span></li>')
        }
        addToggleToH5(sizeFilter, false, ul);
        selector = '.clth_size_filter';
        linkCheckedFilter(selector, staticFilter.refineFilter.size, 'clothes');
    }

    function addColorFilter (r){
        r = r.colors;
        var selector = '#filter-clothes';
        $(selector).append(('<hr><div id="color_filter" class="line"><h5>'+locale.label_color+'</h5>' +
        '<ul class="list-inline twoRows"></ul></div></div>'));
        selector = '#color_filter';
        var colorFilter = $(selector);
        var ul = colorFilter.find('ul');
        for(var i=0; i< r.length; i++){
            ul.append('<li><span><input type="checkbox" class="color_filter"><label>'+resolveLocale(r[i].color)+'</label>' +
                '<input hidden class="id" value="'+r[i].id+'"></span></li>')
        }
        addToggleToH5(colorFilter, false, ul);
        selector = '.color_filter';
        linkCheckedFilter(selector, staticFilter.refineFilter.color, 'clothes');
    }

    function getClothesFilters (){
        $.ajax({
            url: magic + "../../manage/ajax/get/filter/clothes",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            success: function(result){
                console.log(result);
                addGenderFilter(result);
                addClothesCategoryFilter(result);
                addClothesBrandFilter(result);
                addClothesSizeFilter(result);
                addColorFilter(result);
                setTimeout(function(){
                    changeSideBarHeight(sidebar);
                }, 150);
            },
            error: function(result){
                console.log(result)
            }
        });
    }

    /**
     * Method for adding toggle effect to filter options list;
     * @param object object that contains 'h5' and 'ul' elements;
     * @param hidden trigger flag;
     * @param ul list element to make toggle.
     */
    function addToggleToH5 (object, hidden, ul){
        object.find('h5').click(function(){
            object.find('ul').toggle(100);
            if(!hidden) setTimeout(function(){object.addClass('left-line');},100);
            else object.removeClass('left-line');
            hidden = !hidden;
            changeSideBarHeight(sidebar);
        });
    }

    function escapeRegExp(str) {
        return str.replace(/([.*+?^=!:${}()|\[\]\/\\])/g, "");
    }

    /*****Link checking filter check box to add or to remove from filter array******/
    var c = 1;
    function linkCheckedFilter (selector, array, cat){
        $(selector).each(function(){
            var el = $(this);
            var listElem;
            var parent= el.parent();
            var name = parent.find('label').text().trim();
            var id = parent.find('.id').val();
            var listSelector = 'c_'+c+transliterate(name.replace(new RegExp(escapeRegExp(' '), 'g'),''));
            el.change(function(){
                var s;
                if(this.checked) {
                    filter_list.append('<li class="checked filter '+listSelector+'">'+name+'<button></button></li>');
                    s = '.'+listSelector;
                    linkFilterListToFilterPanel(s, el, id, array);
                    array.push(id);
                }
                else {
                    s = '.'+listSelector;
                    listElem = $(s);
                    listElem.remove();
                    removeFromArray(id, array);
                }
                staticFilter.filter.pge=0;
                refreshItems();
            });
            c++;
        })
    }

    function removeFromArray (id, array){
        if(array.length == 1) array.splice(0, 1);
        for(var i =0; i< array.length; i++){
            if(array[i] == id) array.splice(i, 1);
        }
    }

    /**** Localization stuff****/
    function resolveI18n (){
        var total = staticFilter.filter.totalItems;
        $('.view_per_page').each(function(){
            var r = staticFilter.filter.pgeSize;
            if(r == 4) r = 8;
            else r = 4;
            var result='';
            if(total <= staticFilter.filter.pgeSize || total == 0) result = "";
            else if (total < r && total > 0) result = locale.view_all;
            else result = locale.grid_view_items_per_page.replace('{0}',''+r);
            $(this).text(result)
        });
        $('.items_found').each(function(){
            var result = locale.grid_items_number.replace('{0}', ''+total);
            if(total > 1 || total == 0) result = result.replace('{1}', locale.many_items);
            else if(total == 1) result = result.replace('{1}', locale.single_item);
            $(this).text(result)
        });
    }

    $(window).on("resize", (function(_this) {
        return function(e) {
            recalcTabsWidth();
        };
    })(this));
    function recalcTabsWidth(){
        var tabsWidth = tabs.width();
        tabsLi.css('min-width',tabsWidth/4);
        tabsLi.css('width',tabsWidth/4);
    }
    linkCheckedGlobalFilter($('#global_0'));
    recalcTabsWidth();
    sliderInit();

    function changeViews (toRegister, show, action) {
            toRegister.click(function(){
                var t = $(this);
                toRegister.hide();
                var switchDiv = t.parent();
                var panel = $('.action-panel');
                $(show).show();
                staticFilter.displayAsList = action;
                $('.item').remove();
                if(action) {
                    panel.show();
                    drawList(staticFilter.items, currentTab.find('table.list-items'))
                }
                else {
                    panel.hide();
                    panel.find('.left-side').hide();
                    drawGrid(staticFilter.items, itemGridDiv)
                }
            });
    }

    function init(sortSelect){
        sortSelect.on('change',(function(){
            staticFilter.filter.option = sortSelect.val();
            refreshItems()
        }));
    }

    setTimeout(function(){
        $('.main-check').change(function(){
            onCheckBoxSelected(this.checked);
            displayLeftSide(this.checked, true);
            console.log(staticFilter.updates.id)
        });
        init(currentTab.find('select.sort-by'));
    }, 100);
    changeViews($('.thumbnail-view'), '.list-view', false);
    changeViews($('.list-view'), '.thumbnail-view', true);

    function fillExpressEditItemGroup (list, result){
        for(var k=0; k< result.length; k++) {
            list.append('<a>' + resolveLocale(result[k].groupName) +
                '<input hidden type="number" value="'+result[k].id+'"></a>')
        }
        list.find('a').click(function(){
            var btn = list.parent().find('button');
            var ths = $(this);
            btn.text(ths.text());
            btn.append('<input hidden type="number" value="'+ths.find('input').val()+'">');
        });
    }

    function fillExpressEditColor (list, result){
        list.find('a').remove();
        for(var k=0; k< result.length; k++) {
            list.append('<a><input hidden type="number" value="'+result[k].id+'">' +
                '<span class="hex" style="background: '+result[k].hex+'"></span></a>')
        }
        list.find('a').click(function(){
            var btn = list.parent().find('button');
            var ths = $(this);
            btn.find('input').val(ths.find('input').val()); //adding ID
            btn.find('span').css({'background':ths.find('span').css('background')}); //changing color preview
        });
    }

    function getExpressEditInfo(data, url, list){
        $.ajax({
            url: magic + url,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            type: "POST",
            data: data,
            success: function(result){
                fillExpressEditItemGroup(list[0], result.itemGroup);
                fillExpressEditColor(list[1], result.color);
            },
            error: function(result){
                console.log(result)
            }
        });
    }

    function linkExpressEdit (items){
        var leftSide = currentTab.find('.left-side');
        var itemGroupList = editClass.find('.group-content');
        items.each(function(){
            var i = $(this);
            var id = parseInt(i.find('.id').text());
            var name = i.find('.item-name a').text();
            var sale = parseInt(i.find('.sale .price').text());
            var origin = parseInt(i.find('.origin-price .price').text());
            var status_switch = i.find('.switch-active input');
            identifiers.push(id);
            i.find('.check').change(function(){
                if(this.checked) staticFilter.updates.id.push(id);
                else {
                    removeFromArray(id,staticFilter.updates.id);
                    $('.main-check').prop('checked', false);
                }
                displayLeftSide(this.checked, false, leftSide);
            });
            status_switch.change(function(){
                onCheckBoxSelected(false);
                leftSide.fadeOut(100);
                staticFilter.updates.id.push(id);
                var options = {isActive: this.checked};
                if(this.checked){
                    console.log('activate item with id: '+ id)}
                else{
                    console.log('DE activate item with id: '+ id);
                }
                $.when(sendUpdateSingleItem(options, false)).then(function(result){
                    setTimeout(function(){
                        if(currentTab != allTab) {
                            console.log('total: ' + staticFilter.filter.totalItems);
                            if (staticFilter.items.length == 1) staticFilter.filter.pge=0;
                            refreshItems();
                        }
                    },300);
                });
                staticFilter.updates.id =[];
            });
            i.find('.expressEdit').click(function(){
                var itemGroupBtn = itemGroupList.parent().find('button');
                itemGroupBtn.text(i.find('.item-group-val').val());
                tempItemOpt ={
                    itemGroupId: i.find('.item-group-id').val(),
                    itemGroupCat: i.find('.item-group-cat').val(),
                    colorId: i.find('.color-id').val(),
                    colorHex: i.find('.color-hex').val()
                };
                itemGroupBtn.append('<input hidden type="number" value="'+tempItemOpt.itemGroupId+'">');
                var color = editClass.find('button.color');
                color.find('span#hex').css({'background':tempItemOpt.colorHex});
                color.find('input').val(tempItemOpt.colorId);
                itemGroupList.find('a').remove();
                currentItem = i;
                getExpressEditInfo({cat:tempItemOpt.itemGroupCat}, "../../manage/ajax/get/expressEditInfo", [itemGroupList,editClass.find('li.color').find('.dropdown-content')]);
                editClass.find('.id').val(id);
                editClass.find('img.preview').attr('src',i.find('.thumb img').attr('src'));
                editClass.find('.origin-price').find('.price').text(origin);
                var salePriceLeft = editClass.find('.sale-price').find('.price');
                var salePrice = editClass.find('#sale');
                var discount = editClass.find('#discount');
                var offer = editClass.find('.offer');
                var tmp = -1*parseInt(i.find('.discount-logo .last').text());
                if(isNaN(tmp)) tmp = 0;
                discount.val(tmp);
                if(discount.val() > 0){
                    offer.fadeIn(100);
                    editClass.find('.last').text('-'+discount.val()+'%');
                }else offer.fadeOut(100);
                salePriceLeft.text(sale - sale*discount.val()/100);
                salePrice.val(sale);
                var itemName = editClass.find('#item-name');
                itemName.val(name);
                itemName.bind('input propertychange', function(){
                    editClass.find('.update-button').prop('disabled', !checkInputTextField(itemName));
                });
                salePrice.bind('input propertychange', function(){
                    checkInputNumberField(salePrice,0);
                    var price = salePrice.val();
                    if(discount.val() > 0) {
                        price = Math.round(price-salePrice.val()*discount.val()/100).toFixed(1);
                    }
                    salePriceLeft.text(price);
                });
                discount.bind('input propertychange', function(){
                    var d = discount.val();
                    if(d > 0) {
                        offer.fadeIn(100);
                        editClass.find('.last').text('-'+d+'%');
                    }
                    else {
                        offer.fadeOut(100);
                        d = 0;
                    }
                    checkInputNumberField(discount, 0, 70);
                    salePriceLeft.text(Math.round(salePrice.val()-salePrice.val()*d/100).toFixed(1));
                });
                editClass.find('.activate input').prop('checked', status_switch.prop('checked'));
            });
        });
    }

    function initTableDropdown (){
        setTimeout(function(){
            currentTab.find('.list-items').find('.dropbtn').click(function(e){
                $(this).parent().find('.dropdown-content').toggle(150);
                displayLeftSide();
                onCheckBoxSelected(false)
            });
            linkExpressEdit($('.item'));
        },150);
    }

    function sendUpdateSingleItem (options, all){
        var update = JSON.stringify({
            options: options,
            identifiers: staticFilter.updates.id
        });
        $.ajax({
            url: magic + "../../manage/ajax/update/item/single",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            type: "POST",
            data: update,
            success: function(result){
                displayLeftSide();
                onCheckBoxSelected(false);
                if(staticFilter.updates.id.length ==  staticFilter.items.length)  staticFilter.filter.pge = 0;
                if(all) changeAllCheckInput(options.isActive);
                staticFilter.updates.id =[];
            },
            error: function(result){
                console.log(result);
                alert('error');
            }
        });
    }

    function changeAllCheckInput(condition){
        if(currentTab == allTab) $('.switch-active').find('input').prop('checked', condition);
        else refreshItems();
    }

    $('.status_a').click(function(){
        sendUpdateSingleItem({isActive: true},true)
    });
    $('.status_b').click(function(){
        sendUpdateSingleItem({isActive: false},true)
    });
    $('.sort-by').text();

    function initPopupDropDown (){
        $('.right').find('.dropbtn').click(function(e){
            $(this).parent().find('.dropdown-content').toggle(150);
        });
    }

    initPopupDropDown();
    /*window.onclick = function(event) {
        if (event.target.className != 'dropbtn') {
            $('.dropdown-content').hide();
        }
    };*/
    editClass.popup({
        opacity: 0.3,
        transition: 'all 0.3s',
        openelement: '.expressEdit',
        closeelement: '.edit_close'
    });
    $('.currency-value').click(function(){
        $('#currency-select').text($(this).text());
    });
    $('.color-value').click(function(){
        $('#color-select').text($(this).text());
    });
    editClass.find('button.update-button').click(function(){
        staticFilter.updates.id = [];
        staticFilter.updates.id.push(editClass.find('.id').val());
        var colorId = editClass.find('li.color input').val();
        var itemGroupId = editClass.find('button.item-group input').val();
        var options = {
            salePrice: editClass.find('#sale').val()*100,
            itemName:  editClass.find('#item-name').val(),
            discount: editClass.find('#discount').val(),
            isActive: editClass.find('.activate input').prop('checked')
        };
        if(tempItemOpt.colorId !=  colorId) options.color = colorId;
        if(tempItemOpt.itemGroupId != itemGroupId) options.itemGroup = itemGroupId;
        console.log(options);
        $.when(sendUpdateSingleItem(options, false)).then(function(){
            setTimeout(function(){
                refreshItems();
            },250);
        });
    });
    $('h4#options').click(function(){
        var parent = $(this).parent().parent();
        parent.find('.main-option').toggle();
        parent.find('.item-group').toggle();
    });

    function resolveGender(text){
        if(text == 'Men' || text =='Мужское' || text == 'Чоловіче') return 0;
        else if(text == 'Women' || text == 'Женское' || text =='Жіноче') return 1;
    }

    globalEdit.popup({
        opacity: 0.3,
        transition: 'all 0.3s',
        openelement: '.globalEdit',
        closeelement: '.global_close'
    });
    globalEdit.find('.update-button').click(function(){
        var price = globalEdit.find('.sale input');
        var inp = globalEdit.find('.discount input');
        var options = {
            priceValue: price.val()*100
        };
        if(!inp.prop('disabled')) {
            options.discount = inp.val();
            inp.val(0);
            inp.prop('disabled',true);
        }
        price.val(0);
        sendUpdateItems(options)
    });
    globalEdit.find('.down h4').click(function(){
        var inp = globalEdit.find('.discount input');
        inp.prop('disabled', !inp.prop('disabled'));
    });
    function sendUpdateItems(options){
        var update = JSON.stringify({
            options: options,
            identifiers: staticFilter.updates.id
        });
        console.log(update);
        $.ajax({
            url: magic + "../../manage/ajax/update/item/multi",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrf.header, csrf.token);
            },
            dataType: "html",
            contentType: 'application/json',
            mimeType: 'application/json',
            type: "POST",
            data: update,
            success: function(result){
                displayLeftSide();
                if(staticFilter.updates.id.length ==  staticFilter.items.length)  staticFilter.filter.pge = 0;
                if(saleTab) refreshItems();
                staticFilter.updates.id =[];
            },
            error: function(result){
                console.log(result);
                alert('error');
            }
        });
    }
});
/*]]>*/