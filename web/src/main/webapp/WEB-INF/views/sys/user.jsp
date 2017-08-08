<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/2 0002
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    String path = request.getContextPath();
    request.setAttribute("path", path);
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>用户列表</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="${path}/statics/css/bootstrap.min.css">
    <link rel="stylesheet" href="${path}/statics/css/font-awesome.min.css">
    <link rel="stylesheet" href="${path}/statics/plugins/jqgrid/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="${path}/statics/plugins/ztree/css/metroStyle/metroStyle.css">
    <link rel="stylesheet" href="${path}/statics/css/main.css">
    <script src="${path}/statics/libs/jquery.min.js"></script>
    <script src="${path}/statics/plugins/layer/layer.js"></script>
    <script src="${path}/statics/libs/bootstrap.min.js"></script>
    <script src="${path}/statics/libs/vue.min.js"></script>
    <script src="${path}/statics/plugins/jqgrid/grid.locale-cn.js"></script>
    <script src="${path}/statics/plugins/jqgrid/jquery.jqGrid.min.js"></script>
    <script src="${path}/statics/plugins/ztree/jquery.ztree.all.min.js"></script>
    <script src="${path}/js/common.js"></script>
</head>
<body>

 <div id="rrapp" v-cloak>
    <div v-show="showList">
        <div class="grid-btn">


        <sec:authorize url='sys:user:list,sys:user:info'>
            <div class="form-group col-sm-2">
                <input type="text" class="form-control" v-model="q.username" @keyup.enter="query" placeholder="用户名">
            </div>
            <a class="btn btn-default" @click="query">查询</a>
        </sec:authorize>

        <sec:authorize url='sys:user:save,sys:role:select'>
             <a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
        </sec:authorize>
        <sec:authorize url='sys:user:update,sys:role:select'>
             <a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
        </sec:authorize>
        <sec:authorize url='sys:user:delete'>
             <a class="btn btn-primary" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</a>
        </sec:authorize>
         </div>
        <table id="jqGrid"></table>
        <div id="jqGridPager"></div>
    </div>

    <div v-show="!showList" class="panel panel-default">
        <div class="panel-heading">{{title}}</div>
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2 control-label">用户名</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.username" placeholder="登录账号"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">密码</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.password" placeholder="密码"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">邮箱</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.email" placeholder="邮箱"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">手机号</div>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="user.mobile" placeholder="手机号"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">角色</div>
                <div class="col-sm-10">
                    <label v-for="role in roleList" class="checkbox-inline">
                        <input type="checkbox" :value="role.roleId" v-model="user.roleIdList">{{role.roleName}}
                    </label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label">状态</div>
                <label class="radio-inline">
                    <input type="radio" name="status" value="0" v-model="user.status"/> 禁用
                </label>
                <label class="radio-inline">
                    <input type="radio" name="status" value="1" v-model="user.status"/> 正常
                </label>
            </div>
            <div class="form-group">
                <div class="col-sm-2 control-label"></div>
                <input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
                &nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
            </div>
        </form>
    </div>
</div>



<script src="${path}/js/sys/user.js?_${date.systemTime}"></script>
</body>
</html>































