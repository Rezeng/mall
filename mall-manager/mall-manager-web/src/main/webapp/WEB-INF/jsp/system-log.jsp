<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="Bookmark" href="/favicon.ico" >
    <link rel="Shortcut Icon" href="/favicon.ico" />
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
    <link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>系统日志</title>
</head>
<style>
    .table>tbody>tr>td{
        text-align:center;
    }
</style>
<body>

<div>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 系统管理 <span class="c-gray en">&gt;</span> 系统日志 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <form class="page-container">
        <div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a></span> <span class="r">共有数据：<strong id="num">0</strong> 条</span> </div>
        <div class="mt-20">
            <div class="mt-20" style="margin-bottom: 70px">
                <table class="table table-border table-bordered table-bg table-hover table-sort" width="100%">
                    <thead>
                    <tr class="text-c">
                        <th width="25"><input type="checkbox" name="" value=""></th>
                        <th width="30">ID</th>
                        <th width="60">日志名称</th>
                        <th width="60">类型</th>
                        <th width="80">请求路径</th>
                        <th width="70">请求类型</th>
                        <th width="150">请求参数</th>
                        <th width="80">登录用户</th>
                        <th width="100">IP</th>
                        <th width="100">IP位置</th>
                        <th width="70">耗时(毫秒)</th>
                        <th width="100">创建时间</th>
                        <th width="50">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </form>
</div>

<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="lib/datatables/dataTables.colReorder.min.js"></script>
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">

    /*刷新表格*/
    function refresh(){
        var table = $('.table').DataTable();
        table.ajax.reload(null,false);// 刷新表格数据，分页信息不会重置
    }

    /*时间转换*/
    function date(data){
        if(data==null||data==""){
            return "";
        }
        var time = new Date(data);
        var y = time.getFullYear();//年
        var m = time.getMonth() + 1;//月
        var d = time.getDate();//日
        var h = time.getHours();//时
        if (h >= 0 && h <= 9) {
            h = "0" + h;
        }
        var mm = time.getMinutes();//分
        if (mm >= 0 && mm <= 9) {
            mm = "0" + mm;
        }
        return (y+"-"+m+"-"+d+" "+h+":"+mm);
    }

    /*datatables配置*/
    $(document).ready(function () {
        $('.table').DataTable({
            "processing": true,//加载显示提示
            "ajax": {
                url:"/sys/log",
                type: 'GET',
                error:function(XMLHttpRequest){
                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});
                }
            },
            "columns": [
                { "data": null,
                    render : function(data,type, row, meta) {
                        return "<input name=\"checkbox\" value=\""+row.id+"\" type=\"checkbox\" value=\"\">";
                    }
                },
                { "data": "id"},
                { "data": "name"},
                {
                    "data": "type",
                    render: function (data, type, row, meta) {
                        if (data == 1) {
                            return "<span class=\"label label-success radius td-status\">业务日志</span>";
                        } else if (data == 0) {
                            return "<span class=\"label label-danger radius td-status\">错误日志</span>";
                        } else {
                            return "<span class=\"label label-warning radius td-status\">其它日志</span>";
                        }
                    }
                },
                { "data": "url"},
                { "data": "requestType"},
                { "data": "requestParam"},
                { "data": "user"},
                { "data": "ip"},
                { "data": "ipInfo"},
                { "data": "time"},
                { "data": "createDate",
                    render : function(data,type, row, meta) {
                        return date(data);
                    }
                },
                {
                    "data": null,
                    render : function(data,type, row, meta) {
                        return "<a title=\"删除\" href=\"javascript:;\" onclick=\"log_del(this,"+row.id+")\" class=\"ml-5\" style=\"text-decoration:none\"><i class=\"Hui-iconfont\">&#xe6e2;</i></a>";
                    }
                }
            ],
            "aaSorting": [[ 11, "desc" ]],//默认第几个排序
            "bStateSave": false,//状态保存
            "aoColumnDefs": [
                {"orderable":false,"aTargets":[0,12]}// 制定列不参与排序
            ],
            language: {
                url: '/lib/datatables/Chinese.json'
            },
            colReorder: true
        });

        logCount();
    });

    function logCount() {
        $.ajax({
            url:"/sys/log/count",
            type: 'GET',
            success:function (data) {
                if(data.success!=true){
                    layer.alert(data.message,{title: '错误信息',icon: 2});
                    return;
                }
                $("#num").html(data.result);
            },
            error:function(XMLHttpRequest){
                if(XMLHttpRequest.status!=200){
                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status,{title: '错误信息',icon: 2});
                }
            }
        });
    }

    /*订单-删除*/
    function log_del(obj,id){
        layer.confirm('确认要删除ID为\''+id+'\'的日志吗？',{icon:0},function(index){
            $.ajax({
                type: 'DELETE',
                url: '/sys/log/del/'+id,
                dataType: 'json',
                success: function(data){
                    if(data.success!=true){
                        layer.alert(data.message,{title: '错误信息',icon: 2});
                        return;
                    }
                    logCount();
                    refresh();
                    layer.msg('已删除!',{icon:1,time:1000});
                },
                error:function(XMLHttpRequest){
                    layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});
                }
            });
        });
    }

    /*批量删除*/
    function datadel() {
        var cks=document.getElementsByName("checkbox");
        var count=0;
        for(var i=0;i<cks.length;i++){
            if(cks[i].checked){
                count++;
            }
        }
        if(count==0){
            layer.msg('您还未勾选任何数据!',{icon:5,time:3000});
            return;
        }
        layer.confirm('确认要删除所选的'+count+'条数据吗？',{icon:0},function(index){
            for(var i=0;i<cks.length;i++){
                if(cks[i].checked){
                    $.ajax({
                        type: 'DELETE',
                        url: '/sys/log/del/'+cks[i].value,
                        dataType: 'json',
                        success:function(data){
                            if(data.success!=true){
                                layer.alert(data.message,{title: '错误信息',icon: 2});
                            }
                        },
                        error:function(XMLHttpRequest){
                            layer.alert('数据处理失败! 错误码:'+XMLHttpRequest.status+' 错误信息:'+JSON.parse(XMLHttpRequest.responseText).message,{title: '错误信息',icon: 2});
                        }
                    });
                }
            }
            layer.msg('已删除!',{icon:1,time:1000});
            logCount();
            refresh();
        });
    }

    function msgSuccess(content){
        layer.msg(content, {icon: 1,time:3000});
    }
</script>
</body>
</html>