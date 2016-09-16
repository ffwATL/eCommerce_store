/*<![CDATA[*/
$(function(){
    var option = $('#sort_all');
    var minPriceSale = 0;
    var maxPriceSale = 1000;
    var minPriceOrig = minPriceSale;
    var maxPriceOrig = maxPriceSale;
    var cat = 'all';


    function getUrlVars(){
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for(var i = 0; i < hashes.length; i++){
            hash = hashes[i].split('=');
            vars.push({name: hash[0], value: hash[1]});
        }
        return vars;
    }
    var v = getUrlVars();
    for(var i=0; i< v.length; i++){
        switch (v[i].name){
            case 'minPriceSale': {
                minPriceSale = parseFloat(v[i].value);
                break;
            }
            case 'maxPriceSale': {
                maxPriceSale = parseFloat(v[i].value);
                break;
            }
            case 'minPriceOrig':{
                minPriceOrig = parseFloat(v[i].value);
                break;
            }
            case 'maxPriceOrig':{
                maxPriceOrig = parseFloat(v[i].value);
                break;
            }
            case 'cat':{
                cat = v[i].value;
                break;
            }
        }
    }
    var magic =/*[[@{/}]]*/'';
    var addNext = function(currentPage, totalPages, paginationDiv, pgeSize, option){
        if(currentPage < (totalPages-1)){
            var urlNext = magic + '?pge='+(currentPage + 1)+'&pgeSize='+pgeSize+'&option='+option+
                '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
            var nxt = 'Next >>';
            paginationDiv.append('<a href="'+urlNext+'"><button>'+nxt+'</button></a>');
        }
    };

    var addLastPage = function(currentPage, totalPages, paginationDiv, pgeSize, option){
        var url = magic+'?pge='+(totalPages-1)+'&pgeSize='+pgeSize+'&option='+option+
            '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
        if(currentPage + 2 < (totalPages - 1)) paginationDiv.append(' ... ');
        if((totalPages-1) == currentPage) paginationDiv.append('<a href="'+url+'"><button class="active">'+totalPages+'</button></a>');
        else paginationDiv.append('<a href="'+url+'"><button>'+(totalPages)+'</button></a>');
    };

    var addPrevious = function(currentPage, paginationDiv, pgeSize, option){
        var urlPrev = magic + '?pge='+(currentPage - 1)+'&pgeSize='+pgeSize+'&option='+option+
            '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
        var prvs = '<< Previous';
        paginationDiv.append('<a href="'+urlPrev+'"><button>'+prvs+'</button></a>');
    };

    var addFirstPage = function(paginationDiv, pgeSize, option){
        var url = magic + '?pge=0&pgeSize='+pgeSize+'&option='+option+
            '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
        paginationDiv.append('<a href="'+url+'"><button>1</button></a>');
    };

    var firstCase = function(currentPage, totalPages, paginationDiv, pgeSize, option){
        for(var i = 0; i < totalPages; i++){
            var url = magic + '?pge='+i+'&pgeSize='+pgeSize+'&option='+option+
                '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
            if(i == currentPage) paginationDiv.append('<a href="'+url+'"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a href="'+url+'"><button>'+(i+1)+'</button></a>');
        }
        addNext(currentPage, totalPages, paginationDiv, pgeSize, option);
    };

    var secondCase = function(currentPage, totalPages, paginationDiv, pgeSize, option){
        for(var i = 0; i < 5; i++){
            var url = magic + '?pge='+i+'&pgeSize='+pgeSize+'&option='+option+
                '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
            if(i == currentPage) paginationDiv.append('<a href="'+url+'"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a href="'+url+'"><button>'+(i+1)+'</button></a>');
        }
        var url2 = magic + '?pge='+(totalPages - 1)+'&pgeSize='+pgeSize+'&option='+option+
            '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
        paginationDiv.append(' ... <a href="'+url2+'"><button>'+totalPages+'</button></a>');
        addNext(currentPage, totalPages, paginationDiv, pgeSize, option);
    };


    var thirdCase = function(currentPage, totalPages, paginationDiv, pgeSize, option){
        addPrevious(currentPage, paginationDiv, pgeSize, option);
        addFirstPage(paginationDiv, pgeSize, option);
        paginationDiv.append(' ... ');
        for(var i= currentPage-2; i< (totalPages -1) && i <= (currentPage+2); i++){
            var url = magic + '?pge='+i+'&pgeSize='+pgeSize+'&option='+option+
                '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
            if(i == currentPage) paginationDiv.append('<a href="'+url+'"><button class="active">'+(i+1)+'</button></a>');
            else paginationDiv.append('<a href="'+url+'"><button>'+(i+1)+'</button></a>');
        }
        addLastPage(currentPage, totalPages, paginationDiv, pgeSize, option);
        if(currentPage != totalPages - 1) {
            addNext(currentPage, totalPages, paginationDiv, pgeSize, option);
        }
    };

    var resolvePagination = function (currentPage, totalPages, paginationDiv, pgeSize, sortSelect) {
        var selector = '#s' + $('#sortOption').val();
        $(selector).prop('selected', true);
        var option = sortSelect.val();
        sortSelect.on('change',(function(){
            window.location.href= magic + '?pge='+currentPage+'&pgeSize='+pgeSize+'&option='+sortSelect.val()+
                '&minPriceSale='+minPriceSale+'&maxPriceSale='+maxPriceSale+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
        }));

        if(totalPages == 1) return 0;
        else if(totalPages <= 5){
            if(currentPage > 0) addPrevious(currentPage, paginationDiv, pgeSize, option);
            firstCase(currentPage, totalPages, paginationDiv, pgeSize, option);
        }
        else if(totalPages > 6 && currentPage <= 4){
            if(currentPage > 0) addPrevious(currentPage, paginationDiv, pgeSize, option);
            secondCase(currentPage, totalPages, paginationDiv, pgeSize, option);
        }
        else if(totalPages > 6 && currentPage > 4) thirdCase(currentPage, totalPages, paginationDiv, pgeSize, option);
    };

    resolvePagination(
        parseInt($('#currentPage').val()),
        parseInt($('#totalPages').val()),
        $('#pgnt'),
        parseInt($('#pgeSize').val()),
        option
    );
    var refineBySalePrice = function(data){
        window.location.href= magic+'?minPriceSale='+data.from+'&maxPriceSale='+data.to+'&option='+option.val()+
            '&pgeSize='+parseInt($('#pgeSize').val())+'&maxPriceOrig=' + maxPriceOrig +'&minPriceOrig='+minPriceOrig;
    };
    var refineByOrigPrice = function(data){
        window.location.href= magic+'?minPriceOrig='+data.from+'&maxPriceOrig='+data.to+'&option='+option.val()+
            '&pgeSize='+parseInt($('#pgeSize').val())+'&maxPriceSale=' + maxPriceSale +'&minPriceSale='+minPriceSale;
    };
    $("#slider").ionRangeSlider({
        type: "double",
        grid: true,
        min: 0,
        max: 1000,
        from: minPriceSale,
        to: maxPriceSale,
        onFinish: function (data) {
            refineBySalePrice(data)
        },
        step: 50
    });
    $("#slider2").ionRangeSlider({
        type: "double",
        grid: true,
        min: 0,
        max: 1000,
        from: minPriceOrig,
        to: maxPriceOrig,
        onFinish: function (data) {
            refineByOrigPrice(data)
        },
        step: 50
    });
    var sidebar = $('#sidebar');
    sidebar.css('height',sidebar.height() + 20);
    setTimeout(function(){
        $(function() {
            return $("[data-sticky_column]").stick_in_parent({
                parent: $('.content'),
                spacer: false
            });
        });
    }, 300);
    var sidebarHeight = sidebar.height();
    $('.block-wrapper').css('min-height',sidebarHeight);
    var filterGlobal =  $('#filter-global');
    var filterGlobalHeight =filterGlobal.find('ul').height();
    var firstFilter = false;
    filterGlobal.find('h5').click(function(){
        var ul = $('#filter-global').find('ul');
        if(!firstFilter) firstFilter = changeSideBarHeight(filterGlobalHeight, sidebar, true);
        else firstFilter = changeSideBarHeight(filterGlobalHeight, sidebar, false);
        ul.toggle(100);
    });
    if(cat == 'all'){
        $('#clothes').change(function(){
            if(this.checked){
                window.location.href= magic+'?minPriceOrig='+minPriceOrig+'&maxPriceOrig='+maxPriceOrig+'&option='+option.val()+
                    '&pgeSize='+parseInt($('#pgeSize').val())+'&maxPriceSale='+maxPriceSale+'&minPriceSale='+minPriceSale+'&cat='+ $(this).val();
            }
        });
    }

    var changeSideBarHeight = function(filterHeight, sidebar, dsc){
        if(dsc) sidebar.css('height', sidebarHeight - filterHeight);
        else sidebar.css('height', sidebar.height() + filterHeight + 20);
        $(document.body).trigger("sticky_kit:recalc");
        return dsc;
    };
});
/*]]>*/