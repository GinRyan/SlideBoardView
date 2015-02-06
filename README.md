SlideBoardView
======
![SlideBoardView](https://github.com/GinRyan/SlideBoardView/blob/master/snapshot/snapshot_rec.gif)

	这是一个用于使用“手柄”左右滑动的控件，实现滑动手柄可以展开/隐藏某个布局
	
	各位看官们认为有用，就将就着用吧，如果觉得太笨拙太麻烦，或者有神马改进建议，
	欢迎fork以后提交修改补丁或者提交issue。
	
How to Use:
======

1、在需要的布局文件当中放如下内容：

示例代码：
	
		<!--最外层布局是用于固定横向可滑动范围-->
		...
		<org.gryan.slideboardview.SlideBoardView
			android:id="@+id/slideBoardView1"
			android:layout_width="match_parent"
			android:layout_height="wrap_content"
			android:layout_alignParentBottom="true"
			android:background="#33CCCCCC" >

			<!--这层布局用于限定滑动布局里的布局和控件-->
			<org.gryan.slideboardview.SlideInnerLayout
				android:layout_width="match_parent"
				android:layout_height="wrap_content"
				android:background="#99CC00"
				android:orientation="horizontal" >
				<!--这是滑动柄，可以按照TextView的方式去指定样式-->
				<org.gryan.slideboardview.SlideHandView
					android:id="@+id/flag"
					android:layout_width="40dp"
					android:layout_height="match_parent"
					android:background="#FF8800"
					android:gravity="center"
					android:text="@string/flag" />

					<!-- TODO 这当中可以按照自己的需求填放布局，
					如需横向滑动放更多控件可以使用HorizontalScrollView。-->
					
					...
			</org.gryan.slideboardview.SlideInnerLayout>
		</org.gryan.slideboardview.SlideBoardView>
	...
		
	
2、事件监听

如果需要监听滑动展开/隐藏事件可以使用以下方式实现：
	
		SlideBoardView slideBoardView1 = null;
		slideBoardView1 = (SlideBoardView) findViewById(R.id.slideBoardView1);
		slideBoardView1.setOnCollapseBoardListener(new OnCollapseBoardListener() {

			@Override
			public void onCollapse(boolean isExpanded) {
				//TODO 在这里编写展开/隐藏事件触发后的代码
				...
			}
		});
	

Dependency
======

	android-support-v4.jar :v21.0.3

License
=======

	Copyright 2014 Liang Zheng

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.