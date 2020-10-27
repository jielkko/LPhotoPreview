# LPhotoPreview


#### 介绍
仿微信仿头条图片预览

### 引入
Step 1. Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories:
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```gradle
	dependencies {
	       implementation 'com.github.jielkko:LPhotoPreview:1.0'
	}
```

### 初始化
        //图片加载必须先初始化
        LPhotoPicker.getInstance().setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                //图片加载框架自定义
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public void displayImage(Context context, int path, ImageView imageView) {
                //图片加载框架自定义
                Glide.with(context).load(path).into(imageView);
            }
        });

### 使用
仿头条预览 LPhotoPicker.TOUXIAO
```java
  public ArrayList<LPhotoModel> list = new ArrayList<>();
  list.add(new LPhotoModel(R.drawable.test1));
  list.add(new LPhotoModel(R.drawable.test2));
   LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.TOUXIAO) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(0) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView(mToutiaoImage)
                        .show();
```
       
仿微信预览 LPhotoPicker.WEIXIN
```java
  public ArrayList<LPhotoModel> list = new ArrayList<>();
  list.add(new LPhotoModel(R.drawable.test1));
  list.add(new LPhotoModel(R.drawable.test2));
   LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.WEIXIN) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(0) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView(mToutiaoImage)
                        .show();
```                 

在RecyclerView中使用
```java
 mAdapter.setOnItemClickListener(new MainPhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                LPhotoPicker.getInstance()
                        .builder(mActivity)
                        .setMultiMode(LPhotoPicker.TOUXIAO) //默认头条 LPhotoPicker.TOUXIAO
                        .setPage(position) //默认0 是第一页
                        .setImageList(mData) //图片列表
                        .setImageView((ImageView) view)
                        .show();


            }
        });
```
     
