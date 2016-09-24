
(function($) {
	$.fn.extend({
		smoothproducts: function() {
			// Add some markup & set some CSS
			$('.sp-loading').hide();
			$('.sp-wrap').each(function() {
				$(this).addClass('sp-touch');
				var thumbQty = $('a', this).length;

				// If more than one image
				if (thumbQty > 1) {
					var firstLarge,firstThumb,
						defaultImage = $('a.sp-default', this)[0]?true:false;
					$(this).append('<div class="sp-large"></div><div class="sp-thumbs sp-tb-active"></div>');
					$('a', this).each(function(index) {
						var thumb = $('img', this).attr('src'),
							large = $(this).attr('href'),
							classes = '';
						//set default image
						if((index === 0 && !defaultImage) || $(this).hasClass('sp-default')){
							classes = ' class="sp-current"';
							firstLarge = large;
							firstThumb = $('img', this)[0].src;
							$('.sp-large').append('<a href="' + firstLarge + '" class="sp-current-big"><img src="' + firstLarge + '" alt="" /></a>');
						}else $('.sp-large').append('<a href="' + large + '" class="sp-big"><img src="' + large + '" alt="" /></a>');
						$(this).parents('.sp-wrap').find('.sp-thumbs').append('<a href="" style="background-image:url(' + thumb + ')"'+classes+'></a>');
						$(this).remove();
					});

					$('.sp-wrap').css('display', 'inline-block');
				// If only one image
				} else {
					$(this).append('<div class="sp-large"></div>');
					$('a', this).appendTo($('.sp-large', this)).addClass('.sp-current-big');
					$('.sp-wrap').css('display', 'inline-block');
				}
			});

			// Prevent clicking while things are happening
			$(document.body).on('click', '.sp-thumbs', function(event) {
				event.preventDefault();
			});

			// Is this a touch screen or not?
			$(document.body).on('mouseover', function(event) {
				$('.sp-wrap').removeClass('sp-touch').addClass('sp-non-touch');
				event.preventDefault();
			});

			$(document.body).on('touchstart', function() {
				$('.sp-wrap').removeClass('sp-non-touch').addClass('sp-touch');
			});

			// Clicking a thumbnail
			$(document.body).on('click', '.sp-tb-active a', function(event) {
				event.preventDefault();
				$(this).parent().find('.sp-current').removeClass();
				var big = $('.sp-current-big');
				big.removeClass();
				big.addClass('sp-big');
				var newEq = $(this).index();
				big = $('.sp-large').find('a:eq('+newEq+')');
				big.removeClass('sp-big');
				big.addClass('sp-current-big');
				$(this).addClass('sp-current');
				$(this).parents('.sp-wrap').find('.sp-thumbs').removeClass('sp-tb-active');

				var currentHeight = $(this).parents('.sp-wrap').find('.sp-large').height(),
					currentWidth = $(this).parents('.sp-wrap').find('.sp-large').width();
				$(this).parents('.sp-wrap').find('.sp-large').css({
					overflow: 'hidden',
					height: currentHeight + 'px',
					width: currentWidth + 'px'
				});
				$(this).parents('.sp-wrap').find('.sp-large').hide().fadeIn(100, function() {
					var autoHeight = $(this).parents('.sp-wrap').find('.sp-large img').height();
					$(this).parents('.sp-wrap').find('.sp-large').animate({
						height: autoHeight
					}, 'fast', function() {
						$('.sp-large').css({
							height: 472 + 'px',
							width: 370 + 'px'
						});
					});
					$(this).parents('.sp-wrap').find('.sp-thumbs').addClass('sp-tb-active');
				});
			});

			// Open in Lightbox non-touch
			$(document.body).on('click', '.sp-non-touch .sp-zoom', function(event) {
				var currentImg = $(this).html(),
					thumbAmt = $(this).parents('.sp-wrap').find('.sp-thumbs a').length,
					currentThumb = ($(this).parents('.sp-wrap').find('.sp-thumbs .sp-current').index())+1;
				$(this).parents('.sp-wrap').addClass('sp-selected');
				$('body').append("<div class='sp-lightbox' data-currenteq='"+currentThumb+"'>" + currentImg + "</div>");

				if(thumbAmt > 1){
					$('.sp-lightbox').append("<a href='#' id='sp-prev'></a><a href='#' id='sp-next'></a>");
					if(currentThumb == 1) {
						$('#sp-prev').css('opacity','.1');
					} else if (currentThumb == thumbAmt){
						$('#sp-next').css('opacity','.1');
					}
				}
				$('.sp-lightbox').fadeIn();
				event.preventDefault();
			});

			// Open in Lightbox touch
			$(document.body).on('click', '.sp-large a', function(event) {
				var currentImg = $(this).attr('href'),
					thumbAmt = $(this).parents('.sp-wrap').find('.sp-thumbs a').length,
					currentThumb = ($(this).parents('.sp-wrap').find('.sp-thumbs .sp-current').index())+1;

				$(this).parents('.sp-wrap').addClass('sp-selected');
				$('body').append('<div class="sp-lightbox" data-currenteq="'+currentThumb+'"><img src="' + currentImg + '"/></div>');

				if(thumbAmt > 1){
					$('.sp-lightbox').append("<a href='#' id='sp-prev'></a><a href='#' id='sp-next'></a>");
					if(currentThumb == 1) {
						$('#sp-prev').css('opacity','.1');
					} else if (currentThumb == thumbAmt){
						$('#sp-next').css('opacity','.1');
					}
				}
				$('.sp-lightbox').fadeIn();
				event.preventDefault();
			});

			// Pagination Forward for lightbox
			$(document.body).on('click', '#sp-next', function(event) {
				event.stopPropagation();
				var currentEq = $('.sp-lightbox').data('currenteq'),
					totalItems = $('.sp-selected .sp-thumbs a').length;
				if(currentEq >= totalItems) {
				} else {
					swapImages(currentEq-1, currentEq);
					var nextEq = currentEq + 1,
						newImg = $('.sp-large').find('a:eq('+currentEq+')').attr('href');
					if (currentEq == (totalItems - 1)) {
						$('#sp-next').css('opacity','.1');
					}
					$('#sp-prev').css('opacity','1');
					$('.sp-selected .sp-current').removeClass();
					$('.sp-selected .sp-thumbs a:eq('+currentEq+')').addClass('sp-current');
					$('.sp-lightbox img').fadeOut(100, function() {
						$(this).remove();
						$('.sp-lightbox').data('currenteq',nextEq).append('<img src="'+newImg+'"/>');
						$('.sp-lightbox img').hide().fadeIn(100);
					});
				}
				event.preventDefault();
			});

		// Pagination Backward for lightbox
			$(document.body).on('click', '#sp-prev', function(event) {
				event.stopPropagation();
				var currentEq =($('.sp-lightbox').data('currenteq')) -1;
					if(currentEq <= 0) {
					} else {
						if (currentEq == 1) {
							$('#sp-prev').css('opacity','.1');
						}
						swapImages(currentEq, currentEq-1);
						var nextEq = currentEq - 1,
						newImg = $('.sp-large').find('a:eq('+nextEq+')').attr('href');
						$('#sp-next').css('opacity','1');
						$('.sp-selected .sp-current').removeClass();
						$('.sp-selected .sp-thumbs a:eq('+nextEq+')').addClass('sp-current');
						$('.sp-lightbox img').fadeOut(100, function() {
							$(this).remove();
							$('.sp-lightbox').data('currenteq',currentEq).append('<img src="'+newImg+'"/>');
							$('.sp-lightbox img').hide().fadeIn(100);
						});
					}
				event.preventDefault();
			});

			// Close Lightbox
			$(document.body).on('click', '.sp-lightbox', function() {
				closeModal();
			});

			// Close on Esc
			$(document).keydown(function(e) {
				if (e.keyCode == 27) {
					closeModal();
					return false;
				}
			});

			function closeModal (){
				$('.sp-selected').removeClass('sp-selected');
				$('.sp-lightbox').fadeOut(function() {
						$(this).remove();
				});
			}

			/*Pagination forward on click a.next action*/
			function paginationForward(event){
				event.stopPropagation();
				var currentEq = $('.sp-thumbs .sp-current').index(),
					totalItems = $('.sp-thumbs a').length;
				if(currentEq+1 >= totalItems) {
				} else {
					var nextEq = currentEq + 1,
						newImg = $('.sp-thumbs').find('a:eq('+nextEq+')').attr('href');
					if (currentEq == (totalItems - 1)) {
						$('#sp-next').css('opacity','.1');
					}
					$('.sp-current').removeClass();
					$('.sp-thumbs a:eq('+nextEq+')').addClass('sp-current');
					swapImages(currentEq, nextEq);
				}
				event.preventDefault();
			}

			function swapImages(currentEq, nextEq){
				var big = $('.sp-large').find('a:eq('+currentEq+')');
				big.removeClass('sp-current-big');
				big.addClass('sp-big');
				big = big.parent().find('a:eq('+nextEq+')');
				big.removeClass('sp-big');
				big.addClass('sp-current-big');
			}

			/*Pagination backward on click a.prev action*/
			function paginationBackward(event) {
				event.stopPropagation();
				var currentEq = $('.sp-thumbs .sp-current').index();
				if(currentEq <= 0) {
				} else {
					if (currentEq == 1) {
						$('#sp-prev').css('opacity','.1');
					}
					var nextEq = currentEq - 1;
					$('#sp-next').css('opacity','1');
					$('.sp-current').removeClass();
					$('.sp-thumbs a:eq('+nextEq+')').addClass('sp-current');
					swapImages(currentEq, nextEq)
				}
				event.preventDefault();
			}
			// Pagination Forward
			$(document.body).on('click', 'a.next', function(event) {
				paginationForward(event);
			});
			// Pagination backward
			$(document.body).on('click', 'a.prev', function(event) {
				paginationBackward(event);
			});
		}
	});
})(jQuery);