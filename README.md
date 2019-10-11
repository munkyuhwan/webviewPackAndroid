# webviewPackAndroid
웹뷰 페키지 세팅

# 1. Instance

        private WebPackMain wp;

# 2. initiate WebPackMain
    
    wp = new WebPackMain(this);
    
         wp.init(getString(R.string.webview_url), (ConstraintLayout)findViewById(R.id.main_layout) )
         .addPermissionList(Manifest.permission.READ_EXTERNAL_STORAGE)
         .addPermissionList(Manifest.permission.WRITE_EXTERNAL_STORAGE)
         .requestPermissionList()
         .kakaoSetting(true)
         .facebookSetting(true);
         .startLoadingWebview();
                
    메소드 설명
              
        .init(URL, ConstraaintLayout): URL과 layout 셋          
        .addPermissionList(String):  퍼미션 리스트추가
        .requestPermissionList(): 퍼미션 요청
        .kakaoSetting(bool): 카카오 사용여부
        .facebookSetting(bool): 페이스북 사용 여부
        .startLoadingWebview(): 로드 웹뷰
     
     
# 3. backpres handler

        @Override
        public void onBackPressed() {
            wp.backPressClose();
            Log.d("tag","on backpress");
        }

# 4. onRequestPermissionResult

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            wp.requestPermissionResult(requestCode,permissions,grantResults);
            return;
        }


# 5. onActivityResult

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            wp.onActivityResult(requestCode, resultCode, data);
        }



