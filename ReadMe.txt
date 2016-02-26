Dagger
1、查看截图是如何进行的，有图片对比函数
2、将截图功能使用到验证函数中
3、根据验证函数参数进行回放时进行图片的对比

长远计划：java 来读取 testCase文件夹下的文件夹作为测试的 module name
		testCase/module_name/下的js文件作为测试用例：test case
		
		caseAll：默认运行所有的测试用例，这里暂时忽略，后期完善
		
		
		
最新进度：
	
	2016-02-25：项目冗余数据移除 + log 简化 + 日志信息英语化；  
	
	2016-02-18：移除通过读取Excel模板数据进行测试的功能；

	2016-02-18：截图功能保留，但图片比较功能经过测试可用性极差，剔除；
				后期想恢复该功能可直接参考dagger-old——》CheckPageStyle.java；debug调试整理思路；
				
	2016-02-17：已确认：com/netease/imagecheck 下的三个类来完成图片对比，参考 dagger-old——》CheckPageStyle.java
	
	2016-02-17：截图功能已经可用，主要是调用函数 BrowserEmulator#handleFailure；下一步图片对比如何进行；
	
	2016-02-17：已经对02-04的合并操作进行了测试，可以运行；下一步考虑错误截图；
	
	2016-02-04：已经将 dagger中的所有的类合并到 earthWorm ，尚未调试 （重新打包earthworm，查看测试用例是否可以运行）；
	