(function ($) {
    $.fn.beamDialog = function (options) {
        var defaults = {
            title: '标题',
            content: '',
            showCloseButton: true,
            otherButtons: [],
            otherButtonStyles: [],
            bootstrapModalOption: {},
            height:500,
            width:400,
            type:1,//1:iframe 2:内容
            dialogShow: function () {
                //即将显示对话框
            },
            dialogShown: function () {
                //显示对话框
            },
            dialogHide: function () {
                //即将关闭对话框
            },
            dialogHidden: function () {
                //关闭对话框
            },
            clickButton: function (sender, modal, index) {
                // index:选中第几个按钮，sender：按钮对象
                //$(this).closeDialog(modal);
            }
        };
        options = $.extend(defaults, options);
        var modalID = '';

        //生成一个惟一的ID
        function random(a, b) {
            return Math.random() > 0.5 ? -1 : 1;
        }

        function getModalID() {
            return "beamDialog-" + ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'Q', 'q', 'W', 'w', 'E', 'e', 'R', 'r', 'T', 't', 'Y', 'y', 'U', 'u', 'I', 'i', 'O', 'o', 'P', 'p', 'A', 'a', 'S', 's', 'D', 'd', 'F', 'f', 'G', 'g', 'H', 'h', 'J', 'j', 'K', 'k', 'L', 'l', 'Z', 'z', 'X', 'x', 'C', 'c', 'V', 'v', 'B', 'b', 'N', 'n', 'M', 'm'].sort(random).join('').substring(5, 20);
        }

        $.fn.extend({
            closeDialog: function (modal) {
                var modalObj = modal;
                modalObj.modal('hide');
            }
        });

        return this.each(function () {
            var obj = $(this);
            modalID = getModalID();
            var tmpHtml;
            if(options.type==1){
                tmpHtml = '<div class="modal fade" id="{ID}" role="dialog" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"></span></button><h6 class="modal-title">{title}</h6></div><div class="modal-body"></div><div class="modal-footer">{button}</div></div></div></div>';
            } else if(options.type==2){
                tmpHtml = '<div class="modal fade" id="{ID}" role="dialog" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal"><span aria-hidden="true"></span></button><h6 class="modal-title">{title}</h6></div><div class="modal-body">{body}</div><div class="modal-footer">{button}</div></div></div></div>';
            }
            
            var buttonHtml = '<button class="btn btn-primary-close" data-dismiss="modal" aria-hidden="true">关闭</button>';
            if (!options.showCloseButton && options.otherButtons.length > 0) {
                buttonHtml = '';
            }
            //生成按钮
            var btnClass = 'cls-' + modalID;
            var buttonHtmlAdd = '';
            for (var i = 0; i < options.otherButtons.length; i++) {
                buttonHtmlAdd += '<button buttonIndex="' + i + '" class="' + btnClass + ' btn ' + options.otherButtonStyles[i] + '">' + options.otherButtons[i] + '</button>' ;
                if( i == options.otherButtons.length-1){
                    buttonHtml = buttonHtmlAdd + buttonHtml;
                }
            }
            //替换模板标记
            if(options.type==1){
                tmpHtml = tmpHtml.replace(/{ID}/g, modalID).replace(/{title}/g, options.title).replace(/{button}/g, buttonHtml);
                obj.append(tmpHtml);
                obj.find('.modal-body').empty();
                obj.find('.modal-body').load(options.content);
            }else if(options.type==2){
                tmpHtml = tmpHtml.replace(/{ID}/g, modalID).replace(/{title}/g, options.title).replace(/{body}/g, options.content).replace(/{button}/g, buttonHtml);
                obj.append(tmpHtml);
            }
            if (options.width){
                $("#"+modalID).css('width', options.width);
                $("#"+modalID).css('margin-left', function () {
                    if (/%/ig.test(options.width)){
                        return -(parseInt(options.width) / 2) + '%';
                    } else {
                        return -($(this).width() / 2) + 'px';
                    }
                });
            }
            if (options.height){
                $("#"+modalID).css('height', options.height);
                $("#"+modalID).css('margin-top', function () {
                    if (/%/ig.test(options.height)){
                        return -(parseInt(options.height) / 2) + '%';
                    } else {
                        return -($(this).height() / 2) + 'px';
                    }
                });
                obj.find('.modal-body').css('height', options.height - 84 + 'px');
            }

            var modalObj = $('#' + modalID);
            //绑定按钮事件,不包括关闭按钮
            $('.' + btnClass).click(function () {
                var index = $(this).attr('buttonIndex');
                options.clickButton($(this), modalObj, index);
            });
            //绑定本身的事件
            modalObj.on('show.bs.modal', function () {
                options.dialogShow();
            });
            modalObj.on('shown.bs.modal', function () {
                options.dialogShown();
            });
            modalObj.on('hide.bs.modal', function () {
                options.dialogHide();
            });
            modalObj.on('hidden.bs.modal', function () {
                options.dialogHidden();
                modalObj.remove();
            });
            modalObj.modal(options.bootstrapModalOption);
        });

    };

    $.extend({
        beamDialog: function (options) {
            $("body").beamDialog(options);
        }
    });

})(jQuery);