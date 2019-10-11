# webviewPackAndroid
웹뷰 페키지

1. Instance
    private WebPackMain wp;

2. initiate WebPackMain

    wp = new WebPackMain(this);
    wp.init(getString(R.string.webview_url), (ConstraintLayout)findViewById(R.id.main_layout) )
                //필요한 퍼미션 추가
                .addPermissionList(Manifest.permission.READ_EXTERNAL_STORAGE)
                .addPermissionList(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                //.addPermissionList(Manifest.permission.ACCESS_COARSE_LOCATION)
                //.addPermissionList(Manifest.permission.ACCESS_FINE_LOCATION)

                //퍼미션 리퀘스트
                .requestPermissionList()

                //카카오톡 사용 초기화
                //.kakaoSetting(true)

                //페이스북 사용 초기화
                //.facebookSetting(true);
                .startLoadingWebview();
3. backpres handler

    @Override
    public void onBackPressed() {
        wp.backPressClose();
        Log.d("tag","on backpress");
    }

4. onRequestPermissionResult

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("tg","request permission result");
        wp.requestPermissionResult(requestCode,permissions,grantResults);
        return;
    }


5. onActivityResult

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        wp.onActivityResult(requestCode, resultCode, data);
    }



