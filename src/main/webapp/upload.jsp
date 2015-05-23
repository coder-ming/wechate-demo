<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="http://cdn.staticfile.org/webuploader/0.1.1/webuploader.css"> 
<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://cdn.staticfile.org/webuploader/0.1.1/webuploader.html5only.min.js"></script>
</head>
<body>
	<!--dom结构部分-->
<div id="uploader-demo">
    <!--用来存放item-->
    <div id="fileList" class="uploader-list"></div>
    <div id="filePicker">选择图片</div>
</div>
<dir id="list"></dir>
	<script type="text/javascript">
	// 初始化Web Uploader
	var uploader = WebUploader.create({

	    // 选完文件后，是否自动上传。
	    auto: true,
	    fileNumLimit : 6,
	    
	    fileSizeLimit:5000 * 1024,
		fileSingleSizeLimit:500 * 1024,

	    // swf文件路径
	    swf: 'http://cdn.staticfile.org/webuploader/0.1.1/Uploader.swf',

	    // 文件接收服务端。
	    server: 'http://192.168.230.117/ace-java-demo/UploadServlet',

	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#filePicker',

	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    }
	});
	
	// 当有文件添加进来的时候
	/*uploader.on( 'fileQueued', function( file ) {
		var $list = $("#list");
	    var $li = $(
	            '<div id="' + file.id + '" class="file-item thumbnail">' +
	                '<img>' +
	                '<div class="info">' + file.name + '</div>' +
	            '</div>'
	            ),
	    $img = $li.find('img');
	    // $list为容器jQuery实例
	    $list.append( $li );

	    // 创建缩略图
	    // 如果为非图片文件，可以不用调用此方法。
	    // thumbnailWidth x thumbnailHeight 为 100 x 100
	    uploader.makeThumb( file, function( error, src ) {
	        if ( error ) {
	            $img.replaceWith('<span>不能预览</span>');
	            return;
	        }

	        $img.attr( 'src', src );
	    }, thumbnailWidth, thumbnailHeight );
	});*/
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress span');

	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<p class="progress"><span></span></p>')
	                .appendTo( $li )
	                .find('span');
	    }

	    $percent.css( 'width', percentage * 100 + '%' );
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file, response ) {
	    $( '#'+file.id ).addClass('upload-state-done');
	    console.log(response);
	    
	    var $list = $("#list");
	    var $li = $(
	            '<div id="' + file.id + '" class="file-item thumbnail">' +
	                '<img>' +
	               // '<div class="info">' + file.name + '</div>' +
	            '</div>'
	            ),
	    $img = $li.find('img');
	    $img.attr("src", "http://192.168.230.117/ace-java-demo/upload/" + response._raw);
	    // $list为容器jQuery实例
	    $list.append( $li );
	    
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file, reason) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');	
				
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
		
	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	
	//选择文件错误触发事件;
	uploader.on('error', function( code ) {
		var text = '';
		switch( code ) {
			case  'F_DUPLICATE' : text = '该文件已经被选择了!' ;
			break;
			case  'Q_EXCEED_NUM_LIMIT' : text = '上传文件数量超过限制!' ;
			break;
			case  'F_EXCEED_SIZE' : text = '文件大小超过限制!';
			break;
			case  'Q_EXCEED_SIZE_LIMIT' : text = '所有文件总大小超过限制!';
			break;
			case 'Q_TYPE_DENIED' : text = '文件类型不正确或者是空文件!';
			break;
			default : text = '未知错误!';
				break;	
		}
    	alert( text );
	});
		
	</script>
</body>
</html>