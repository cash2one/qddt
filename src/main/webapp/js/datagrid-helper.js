/**
 * Created by zark on 17/3/20.
 */
(function($) {
    $.downloadExcel = function (url, data, method){    // 获得url和data
        if( url && data ){
            // data 是 string 或者 array/object
            //data = typeof data == 'string' ? data : $.param(data);        // 把参数组装成 form的  input
            var inputs = "";
            $.each(data, function(n,d){
                //var pair = this.split('=');
                inputs+="<input type='hidden' name='"+n+"' value='"+ d +"' />";
            });
            // request发送请求
            //console.log(inputs);
            $("<form action='"+ url +"' method='"+ (method||'POST') +"' >"+inputs+"</form>")
                .appendTo('body').submit().remove();
        };
    };

    $.resizeGrid = function (data) {
        //datagrid头部 table 的第一个tr 的td们，即columns的集合
        var headerTds = $(".datagrid-view2 .datagrid-header-inner table tr:first-child").children();
        //datagrid主体 table 的第一个tr 的td们，即第一个数据行
        var bodyTds = $(".datagrid-view2 .datagrid-body table tr:first-child").children();
        var totalWidth = 0; //合计宽度，用来为datagrid头部和主体设置宽度
        //循环设置宽度
        bodyTds.each(function (i, obj) {
            var headerTd = $(headerTds.get(i));
            var bodyTd = $(bodyTds.get(i));
            $("div:first-child", headerTds.get(i)).css("text-align", "center");
            var headerTdWidth = headerTd.width(); //获取第i个头部td的宽度
            //这里加5个像素 是因为数据主体我们取的是第一行数据，不能确保第一行数据宽度最宽，预留5个像素。有兴趣的朋友可以先判断最大的td宽度都在进行设置
            var bodyTdWidth = bodyTd.width() + 5;
            var width = 0;
            //如果头部列名宽度比主体数据宽度宽，则它们的宽度都设为头部的宽度。反之亦然
            if (headerTdWidth > bodyTdWidth) {
                width = headerTdWidth;
                bodyTd.width(width);
                headerTd.width(width);
                totalWidth += width;
            } else {
                width = bodyTdWidth;
                headerTd.width(width);
                bodyTd.width(width);
                totalWidth += width;
            }
        });
        var headerTable = $(".datagrid-view2 .datagrid-header-inner table:first-child");
        var bodyTable = $(".datagrid-view2 .datagrid-body table:first-child");
        //循环完毕即能得到总得宽度设置到头部table和数据主体table中
        headerTable.width(totalWidth);
        bodyTable.width(totalWidth);
        bodyTds.each(function (i, obj) {
            var headerTd = $(headerTds.get(i));
            var bodyTd = $(bodyTds.get(i));
            var headerTdWidth = headerTd.width();
            bodyTd.width(headerTdWidth);
        });
    };

    $.saveGridToExcel = function(url,state,filename,post){
        if (!state || !filename || !url)
            return;
        //var state = $('#dg').data('datagrid');
        var stringRows=JSON.stringify(state.data.rows);
        var columns = state.options.columns[0];
        var titles = new Array();
        $.each(columns,function (i,n) {
            if (n.field != 'operation')
                titles.push({'title':n.title,'field':n.field});
        });
        var data = {'data':stringRows,'titles':JSON.stringify(titles),'filename':filename};
        $.downloadExcel(url,data,(post||'POST'));
    };
    
    $.toggleFilterPanel = function () {
        var theadHeight = $('div.datagrid-header').height();
        var headHeight = $('tr.datagrid-header-row').height();
        var innerHeight = $('tr.datagrid-filter-row').height();
        //alert("max (header height" +headHeight+',header-inner.height) '+ innerHeight);
        if (theadHeight <= innerHeight){
            //$('#dg').datagrid({filterRules:[]}).datagrid('enableFilter');
            $('div.datagrid-header').height(headHeight+innerHeight);
        }
        else {
            //$('#dg').datagrid({filterRules:[]});
            $('div.datagrid-header').height(headHeight);
        }
    };

    $.makeNumberFilter = function (field,precision,op) {
        var filter = {};
        if (field && precision) {
            filter =  {
                field: field,
                type: 'numberbox',
                options: {precision: precision},
                op: (op||['equal', 'notequal', 'less', 'greater'])
            };
        };
        return filter;
    };

    $.makeSelectFilter = function (dg,field,options) {
        var filter = {};
        if (options)
            options.splice(0,0,{value:'',text:'所有'});
        if (dg && field){
            filter = {
                field:field,
                    type:'combobox',
                options:{
                panelHeight:'auto',
                    data:(options||[{value:'',text:'所有'}]),
                    onChange:function(value){
                        if (value == ''){
                            dg.datagrid('removeFilterRule', field);
                        } else {
                            dg.datagrid('addFilterRule', {
                                field: field,
                                op: 'equal',
                                value: value
                            });
                        }
                        dg.datagrid('doFilter');
                    }
                }
            };
        }
        return filter;
    };


})(jQuery);