<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
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
	<script src="${path}/js/common.js"></script>	</head>
<body>
<div id="rrapp" v-cloak>
	<div v-show="showList">
		<div class="grid-btn">

			<sec:authorize url='sys:menu:save,sys:menu:select'>
	 			<a class="btn btn-primary" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</a>
			</sec:authorize>
			<sec:authorize url='sys:menu:update,sys:menu:select'>
 				<a class="btn btn-primary" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</a>
			</sec:authorize>
			<sec:authorize url='sys:menu:delete'>
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
				<div class="col-sm-2 control-label">类型</div> 
				<label class="radio-inline">
				  <input type="radio" name="type" value="0" v-model="menu.type"/> 目录
				</label>
				<label class="radio-inline">
				  <input type="radio" name="type" value="1" v-model="menu.type"/> 菜单
				</label>
				<label class="radio-inline">
				  <input type="radio" name="type" value="2" v-model="menu.type"/> 按钮
				</label>
			</div>
			<div class="form-group">
			   	<div class="col-sm-2 control-label">菜单名称</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="menu.name" placeholder="菜单名称或按钮名称"/>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label">上级菜单</div>
			   	<div class="col-sm-10">
			       <input type="text" class="form-control" style="cursor:pointer;" v-model="menu.parentName" @click="menuTree" readonly="readonly" placeholder="一级菜单"/>
			    </div>
			</div>
			<div v-if="menu.type == 1" class="form-group">
			   	<div class="col-sm-2 control-label">菜单URL</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="menu.url" placeholder="菜单URL"/>
			    </div>
			</div>
			<div v-if="menu.type == 1 || menu.type == 2" class="form-group">
			   	<div class="col-sm-2 control-label">授权标识</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="menu.perms" placeholder="多个用逗号分隔，如：user:list,user:create"/>
			    </div>
			</div>
			<div v-if="menu.type != 2" class="form-group">
			   	<div class="col-sm-2 control-label">排序号</div>
			   	<div class="col-sm-10">
			      <input type="number" class="form-control" v-model="menu.orderNum" placeholder="排序号"/>
			    </div>
			</div>
			<div v-if="menu.type != 2" class="form-group">
			   	<div class="col-sm-2 control-label">图标</div>
			   	<div class="col-sm-10">
			      <input type="text" class="form-control" v-model="menu.icon" placeholder="菜单图标"/>
			      <code style="margin-top:4px;display: block;">获取图标：http://fontawesome.io/icons/</code>
			    </div>
			</div>
			<div class="form-group">
				<div class="col-sm-2 control-label"></div> 
				<input type="button" class="btn btn-primary" @click="saveOrUpdate" value="确定"/>
				&nbsp;&nbsp;<input type="button" class="btn btn-warning" @click="reload" value="返回"/>
			</div>
		</form>
	</div>
</div>

<!-- 选择菜单 -->
<div id="menuLayer" style="display: none;padding:10px;">
	<ul id="menuTree" class="ztree"></ul>
</div>

<script src="${path}/js/sys/menu.js?_${date.systemTime}"></script>
</body>
</html>