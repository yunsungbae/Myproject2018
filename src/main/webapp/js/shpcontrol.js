function shpSave(){
    var form = $('#shpSaveForm')[0];
    var formData = new FormData(form);
    debugger;
    formData.append("fileObj", $("#exampleFormControlFile1")[0].files[0]);
    $.ajax({
        url: 'shpSave.do',
        processData: false,
        contentType: false,
        data: formData,
        type: 'POST',
        success: function(result){
            alert("업로드 성공!!");
        }
    });
}

//
// 출처: http://cofs.tistory.com/181 [CofS]
//
//
//
//     $('#shpSaveForm').ajaxForm({
//         beforeSubmit: function (data,form,option) {
//             //validation체크
//             //막기위해서는 return false를 잡아주면됨
//             return true;
//         },
//         success: function(response,status){
//             //성공후 서버에서 받은 데이터 처리
//             alert("업로드 성공!!");
//         },
//         error: function(){
//             //에러발생을 위한 code페이지
//         }
//     });
//};