<%--
  Created by IntelliJ IDEA.
  User: zark
  Date: 16/11/26
  Time: 下午5:46
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="header.jsp"%>
<div class="container" >


    <div class="panel panel-default">
        <!-- Default panel contents -->
        <%--<div class="panel-heading information btn-info">${message}</div>--%>
        <form action="/assessment/doverify" method="POST">
            <input type="hidden" name="eventid" value="${eventid}"/>
            <div>
                <button type="submit" class="btn btn-default"  >确认提交以下数据</button>
            </div>
        </form>
        <%--<div><p>${rows}</p></div>--%>
        <!-- Table -->
        <table class="easyui-datagrid" title="DataGrid" id="tab"  >
        </table>
        <script type="text/javascript">
            var data = ${rows};
            var titles = [${titles}];

            (function($){
                function pagerFilter(data){
                    if ($.isArray(data)){	// is array
                        data = {
                            total: data.length,
                            rows: data
                        }
                    }
                    var target = this;
                    var dg = $(target);
                    var state = dg.data('datagrid');
                    var opts = dg.datagrid('options');
                    if (!state.allRows){
                        state.allRows = (data.rows);
                    }
                    if (!opts.remoteSort && opts.sortName){
                        var names = opts.sortName.split(',');
                        var orders = opts.sortOrder.split(',');
                        state.allRows.sort(function(r1,r2){
                            var r = 0;
                            for(var i=0; i<names.length; i++){
                                var sn = names[i];
                                var so = orders[i];
                                var col = $(target).datagrid('getColumnOption', sn);
                                var sortFunc = col.sorter || function(a,b){
                                            return a==b ? 0 : (a>b?1:-1);
                                        };
                                r = sortFunc(r1[sn], r2[sn]) * (so=='asc'?1:-1);
                                if (r != 0){
                                    return r;
                                }
                            }
                            return r;
                        });
                    }
                    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
                    var end = start + parseInt(opts.pageSize);
                    data.rows = state.allRows.slice(start, end);
                    return data;
                }

                var loadDataMethod = $.fn.datagrid.methods.loadData;
                var deleteRowMethod = $.fn.datagrid.methods.deleteRow;
                $.extend($.fn.datagrid.methods, {
                    clientPaging: function(jq){
                        return jq.each(function(){
                            var dg = $(this);
                            var state = dg.data('datagrid');
                            var opts = state.options;
                            opts.loadFilter = pagerFilter;
                            var onBeforeLoad = opts.onBeforeLoad;
                            opts.onBeforeLoad = function(param){
                                state.allRows = null;
                                return onBeforeLoad.call(this, param);
                            }
                            var pager = dg.datagrid('getPager');
                            pager.pagination({
                                onSelectPage:function(pageNum, pageSize){
                                    opts.pageNumber = pageNum;
                                    opts.pageSize = pageSize;
                                    pager.pagination('refresh',{
                                        pageNumber:pageNum,
                                        pageSize:pageSize
                                    });
                                    dg.datagrid('loadData',state.allRows);
                                }
                            });
                            $(this).datagrid('loadData', state.data);
                            if (opts.url){
                                $(this).datagrid('reload');
                            }
                        });
                    },
                    loadData: function(jq, data){
                        jq.each(function(){
                            $(this).data('datagrid').allRows = null;
                        });
                        return loadDataMethod.call($.fn.datagrid.methods, jq, data);
                    },
                    deleteRow: function(jq, index){
                        return jq.each(function(){
                            var row = $(this).datagrid('getRows')[index];
                            deleteRowMethod.call($.fn.datagrid.methods, $(this), index);
                            var state = $(this).data('datagrid');
                            if (state.options.loadFilter == pagerFilter){
                                for(var i=0; i<state.allRows.length; i++){
                                    if (state.allRows[i] == row){
                                        state.allRows.splice(i,1);
                                        break;
                                    }
                                }
                                $(this).datagrid('loadData', state.allRows);
                            }
                        });
                    },
                    getAllRows: function(jq){
                        return jq.data('datagrid').allRows;
                    }
                })
            })(jQuery);
            //alert(data.total);
            $('#tab').datagrid({
                /*width:810,*/
                height:500,
                title:"门店本期对投发放清单及明细",
                idField:'yxbm',
                nowrap: false,
                striped: true,
                //fit: true,//自动大小
                rowNumbers:true,
                pagination:true,
                //showFooter: true,
                singleSelect:true,
                pageSize: 20,
                pageList:[10,20,50],
                columns:titles,
            });
            $('#tab').datagrid({data:data}).datagrid('clientPaging');

            //$('#tab').datagrid('loadData',data);
            /*var pager = $('#tab').datagrid().datagrid('getPager');
            pager.pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10,20,50],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
            });*/

            //$.messager.alert('Info',data.total+" rowcount!");
        </script>
    </div>

</div>
<%--<script type="text/javascript">
    $(document).on('ready', function() {
        var data = ${rows};
        var titles = ${titles};
        alert(data.total);
        $('#tab').datagrid({
            width:810,
            height:400,
            idField:'yxbm',
            url:url,
            singleSelect:true,
            columns:  [[{"align":"center","field":"qx","title":"区县","width":"100"},{"align":"center","field":"zj","title":"支局","width":"100"},{"align":"center","field":"pqmc","title":"片区名称","width":"100"},{"align":"center","field":"yxbm","title":"营销编码","width":"100"},{"align":"center","field":"qdid","title":"渠道ID","width":"100"},{"align":"center","field":"mdmc","title":"门店名称","width":"100"},{"align":"center","field":"dykhdf","title":"当月考核得分","width":"100"},{"align":"center","field":"dtbz","title":"对投标准(单位：元）","width":"100"},{"align":"center","field":"dyyfdt","title":"当月应发对投","width":"100"},{"align":"center","field":"dyyingfdt","title":"当月预发对投","width":"100"},{"align":"center","field":"dydtqs","title":"当月对投清算","width":"100"},{"align":"center","field":"xydtyf","title":"下月对投预发","width":"100"},{"align":"center","field":"bqdtsf","title":"本期对投实发","width":"100"}]]
        });
        $.messager.alert('Info',data.total);
        //$('#tab').datagrid('loadData',data);
    });

</script>--%>
<%@ include file="footer.jsp"%>


