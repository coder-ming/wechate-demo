package com.wechat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 DiskFileItemFactory factory = new DiskFileItemFactory();  
		 request.setCharacterEncoding("utf-8");//防止中文名乱码
         int sizeThreshold=1024*6; //缓存区大小
         String basePath = this.getServletContext().getRealPath("/upload/");
         File repository = new File(basePath); //缓存区目录
         if(!repository.exists()) {
        	 repository.mkdir();
         }
         
         long sizeMax = 1024 * 1024 * 2;//设置文件的大小为2M
         final String allowExtNames = "jpg,gif,bmp,rar,rar,txt,docx";
         DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
         diskFileItemFactory.setRepository(repository);
         diskFileItemFactory.setSizeThreshold(sizeThreshold);
         ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
         servletFileUpload.setSizeMax(sizeMax);
        
         List<FileItem> fileItems = null;
         try{
             fileItems = servletFileUpload.parseRequest(request);
             for(FileItem fileItem:fileItems){
	                long size=0;
	                String filePath = fileItem.getName();
	                System.out.println(filePath);
	                if(filePath==null || filePath.trim().length()==0)
	                    continue;
	                String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1);
	                String extName=filePath.substring(filePath.lastIndexOf(".")+1);
	                if(allowExtNames.indexOf(extName)!=-1) {
	                    try {
	                        fileItem.write(new File(basePath+File.separator+fileName));
	                        response.getWriter().write(fileName);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	                else{
	                    throw new FileUploadException("file type is not allowed");
	                }
             }
        }catch(FileSizeLimitExceededException e){
            System.out.println("file size is not allowed");
        }catch(FileUploadException e1){
            e1.printStackTrace();
        }
		
	}

}
