/*
懒人建站 http://www.51xuediannao.com/ 
懒人建站为您提供-基于jquery特效，jquery弹出层效果，js特效代码大全，JS广告代码，导航菜单代码，下拉菜单代码和jquery焦点图片代码。

jQ向上滚动带上下翻页按钮
*/
;(function ($) {
            $.fn.extend({
                Scroll: function (opt, callback) {
                    if (!opt) var opt = {};
                    var _btnUp = $("#" + opt.up); //Shawphy:向上按钮      
                    var _btnDown = $("#" + opt.down); //Shawphy:向下按钮
                    var _this = this.eq(0).find("ul:first");
                    var lineH = _this.find("li:first").outerHeight(); //获取行高 
                    var line = opt.line ? parseInt(opt.line, 10) : parseInt(this.height() / lineH, 10); //每次滚动的行数，默认为一屏，即父容器高度
                    var speed = opt.speed ? parseInt(opt.speed, 10) : 600; //卷动速度，数值越大，速度越慢（毫秒） 
                    var m = 0;  //用于计算的变量
                    var count = _this.find("li").length; //总共的<li>元素的个数
                    var upHeight = line * lineH;
                    var showline = opt.showline;//显示多少行
                    function scrollUp() {
                        if (!_this.is(":animated")) {  //判断元素是否正处于动画，如果不处于动画状态，则追加动画。
                            if (m < count) {  //判断 m 是否小于总的个数
                                var go_count = count-m-showline;
                                if((count-m)>showline){
                                    if(go_count<line){
                                        m += go_count;
                                        upHeight =  go_count * lineH;
                                        _this.animate({ marginTop: "-=" + upHeight + "px" }, speed);
                                    }else{
                                        m += line;
                                        upHeight =  line * lineH;
                                        _this.animate({ marginTop: "-=" + upHeight + "px" }, speed);
                                    }
                                }else{


                                }
                            }
                        }
                    }
                    function scrollDown() {
                        if (!_this.is(":animated")) {
                            if(m>0){
                                if (m > line) { //判断m 是否大于一屏个数
                                    m -= line;
                                    upHeight =  line * lineH;
                                    _this.animate({ marginTop: "+=" + upHeight + "px" }, speed);
                                }else{
                                    upHeight =  m * lineH;
                                    m -= m;
                                    _this.animate({ marginTop: "+=" + upHeight + "px" }, speed);
                                }
                            }
                        }
                    }
                    _btnUp.bind("click", scrollUp);
                    _btnDown.bind("click", scrollDown);
                }
            });
        })(jQuery);