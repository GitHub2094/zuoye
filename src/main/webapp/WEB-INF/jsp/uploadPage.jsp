<%--
  Created by IntelliJ IDEA.
  User: lsm
  Date: 2020/3/21
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<form action="upload" method="post" enctype="multipart/form-data">
    选择图片:<input type="file" name="file" accept="image/*" /> <br>
    <input type="submit" value="上传">
</form>

