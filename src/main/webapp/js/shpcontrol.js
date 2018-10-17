function shpSave(){

    // $('#shpSaveForm').ajaxForm({
    //     url: "/shpSave2.do",
    //     enctype: "multipart/form-data", // 여기에 url과 enctype은 꼭 지정해주어야 하는 부분이며 multipart로 지정해주지 않으면 controller로 파일을 보낼 수 없음
    //     success: function(result){
    //         alert(result);
    //     }
    // });
    //
    // $("#shpSaveForm").submit();




    // $('#shpSaveForm').ajaxfileupload({
    //     action: '/shpSave2.do'
    // });


    // var form = $('#shpSaveForm')[0];
    // var formData = new FormData(form);

    //formData.append("fileObj",form[0].files[0]);
// debugger;
//     $.ajax({
//         url: '/shpSave.do',
//         processData: false,
//         contentType: false,
//         data: formData,
//         type: 'POST',
//         success: function(result){
//       debugger;
//             alert("업로드 성공!!");
//         },
//         error:function(resiult) {
//             debugger;
//             alert("error");
//         }
//
//     });
    var form = $('#shpSaveForm')[0];
    var formData = new FormData();

    formData.append("fileObj",form[0].files[0]);
debugger;
    $.ajax({
        url: '/shpSave2.do',
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        data: formData,
        type: 'POST',
        success: function(result){
            debugger;
            alert("업로드 성공!!");
        },
        error:function(resiult) {
            debugger;
            alert("error");
        }

    });
}
function bs_input_file() {
    $(".input-file").before(
        function() {
            if ( ! $(this).prev().hasClass('input-ghost') ) {
                var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                element.attr("name",$(this).attr("name"));
                element.change(function(){
                    element.next(element).find('input').val((element.val()).split('\\').pop());
                });
                $(this).find("button.btn-choose").click(function(){
                    element.click();
                });
                $(this).find("button.btn-reset").click(function(){
                    element.val(null);
                    $(this).parents(".input-file").find('input').val('');
                });
                $(this).find('input').css("cursor","pointer");
                $(this).find('input').mousedown(function() {
                    $(this).parents('.input-file').prev().click();
                    return false;
                });
                return element;
            }
        }
    );
}

