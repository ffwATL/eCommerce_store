document.addEventListener("DOMContentLoaded", function(){

    var actual = document.getElementsByClassName("actual");
    var expected = document.getElementsByClassName("expected");

    for(var i = 0; i < actual.length; i++) {
        var actHighlighted = compareBlocks(expected[i], actual[i]);
        console.log(actual[i]);
    }

    function compareBlocks(expected, actual) {
        var ex = expected.textContent, ac = actual.textContent;
        var defaultClass = false, changedClass = false, removedClass = false;
        var actualDiv, expectedDiv;

        actual.textContent = '';
        expected.textContent = '';

        if(ex.length >= ac.length) {
            for(var i = 0; i < ex.length; i++) {
                var exVal = ex[i];

                if(i < ac.length) {
                    var acVal = ac[i];

                    if(exVal == acVal) {

                        if(!defaultClass) {
                            actualDiv = createElementAndAppendTo(actual, 'span', 'matched');
                            expectedDiv = createElementAndAppendTo(expected, 'span', 'matched');
                            defaultClass = true;
                            changedClass = false;
                            removedClass = false;
                        }
                    }else {
                        if(!changedClass) {
                            actualDiv = createElementAndAppendTo(actual, 'span', 'differs');
                            expectedDiv = createElementAndAppendTo(expected, 'span', 'default');
                            defaultClass = false;
                            changedClass = true;
                            removedClass = false;
                        }
                    }
                    actualDiv.textContent += acVal;

                }else {
                    if(!removedClass) {
                        actualDiv = createElementAndAppendTo(actual, 'span', 'empty');
                        expectedDiv = createElementAndAppendTo(expected, 'span', 'default');
                        defaultClass = false;
                        changedClass = false;
                        removedClass = true;
                    }
                    actualDiv.textContent +='X';
                }
                expectedDiv.textContent += exVal;
            }
        }else {
            for(var k = 0; k < ac.length; k++) {
                var acVal = ac[k];

                if(k < ex.length) {
                    var exVal = ex[k];

                    if(exVal == acVal) {
                        if(!defaultClass) {
                            actualDiv = createElementAndAppendTo(actual, 'span', 'matched');
                            expectedDiv = createElementAndAppendTo(expected, 'span', 'matched');
                            defaultClass = true;
                            changedClass = false;
                            removedClass = false;
                        }

                    } else {
                        if(!changedClass) {
                            actualDiv = createElementAndAppendTo(actual, 'span', 'differs');
                            expectedDiv = createElementAndAppendTo(expected, 'span', 'default');
                            defaultClass = false;
                            changedClass = true;
                            removedClass = false;
                        }
                    }
                    actualDiv.textContent += acVal;
                    expectedDiv.textContent += exVal;
                }else {
                    if(!removedClass) {
                        actualDiv = createElementAndAppendTo(actual, 'span', 'insertedDiff');
                        defaultClass = false;
                        changedClass = false;
                        removedClass = true;
                    }
                    actualDiv.textContent += acVal;
                }
            }
        }
    }

    function createElementAndAppendTo(container, elemType, clazzName) {
        var elem = document.createElement(elemType);
        elem.className = clazzName;
        container.appendChild(elem);
        return elem;
    }
});