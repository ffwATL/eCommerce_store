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
    var tabs = $('.tabs');
    var allTab = $('.all');
    var inactiveTab = $('.inactive');
    var isActiveTab = $('.isActive');
    var saleTab = $('.sale');
    var currentTab = allTab;
    var filterList = $('.filter-list');
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


});
/*]]>*/
