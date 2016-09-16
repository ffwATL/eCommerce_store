$(function(){
    $('.donut-chart').cssCharts({type:"donut"}).trigger('show-donut-chart');
    $('.pie-chart').cssCharts({type:"pie"}).trigger('show-donut-chart');
    $('.line-chart').cssCharts({type:"line"});
    var dataForSoldEarn = {
        labels: ['30.05', '06.06', '13.06', '20.06','27.06'],
        series: [
            [
                {meta: 'sold', value: 259},
                {meta: 'sold', value: 0},
                {meta: 'sold', value: 459},
                {meta: 'sold', value: 1200},
                {meta: 'sold', value: 550}
            ],
            [
                {meta: 'earn', value: 30},
                {meta: 'earn', value: 0},
                {meta: 'earn', value: 150},
                {meta: 'earn', value: 400},
                {meta: 'sold', value: 200}
            ]
        ]
    };

    /* Set some base options (settings will override the default settings in Chartist.js *see default settings*). We are adding a basic label interpolation function for the xAxis labels. */
    var optForSoldEarn = {
        axisX: {
            labelInterpolationFnc: function(value) {
                return value;
            }
        },
        width: '400px',
        height: '200px',
        showArea: true,
        plugins: [
            Chartist.plugins.tooltip()
        ]
    };

    /* Now we can specify multiple responsive settings that will override the base settings based on order and if the media queries match. In this example we are changing the visibility of dots and lines as well as use different label interpolations for space reasons. */
    var respOptSoldEarn = [
        ['screen and (min-width: 641px) and (max-width: 1024px)', {
            showPoint: true,
            axisX: {
                labelInterpolationFnc: function(value) {
                    return value;
                }
            }
        }],
        ['screen and (max-width: 640px)', {
            showLine: true,
            axisX: {
                labelInterpolationFnc: function(value) {
                    return value;
                }
            }
        }]
    ];

    /* Initialize the chart with the above settings */
    new Chartist.Line('.cline', dataForSoldEarn, optForSoldEarn, respOptSoldEarn);

    var dataMoneyIncome = {
        labels: ['30.05', '06.06', '13.06', '20.06','27.06'],
        series: [
            [
                {meta: 'outcome', value: 0},
                {meta: 'outcome', value: 400},
                {meta: 'outcome', value: 0},
                {meta: 'outcome', value: 500},
                {meta: 'outcome', value: 0}
            ],
            [
                {meta: 'income', value: 259},
                {meta: 'income', value: 0},
                {meta: 'income', value: 459},
                {meta: 'income', value: 200},
                {meta: 'income', value: 550}
            ]

        ]
    };
    var optForMoneyIncome = {
        axisY: {
            position: 'start',
            offset: 40
        },
        seriesBarDistance: 10,
        width: '400px',
        height: '200px',
        plugins: [
            Chartist.plugins.tooltip()
        ]
    };

    new Chartist.Bar('.cbar', dataMoneyIncome, optForMoneyIncome, respOptSoldEarn);
});