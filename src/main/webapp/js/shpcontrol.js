function shpSave(){
    $('#ajaxform').ajaxForm({ //보내기전 validation check가 필요할경우
        beforeSubmit: function (data, frm, opt) { alert("전송전!!"); return true; },
        success: function(responseText, statusText){ alert("전송성공!!"); },
        error: function(){ alert("에러발생!!"); } });




    // var form = $('#shpSaveForm')[0];
    // var formData = new FormData(form);
    //
    // formData.append("fileObj", $("#inputGroupFile01")[0].files[0]);
    // $.ajax({
    //     url: 'shpSave.do',
    //     processData: false,
    //     contentType: false,
    //     data: formData,
    //     type: 'POST',
    //     success: function(result){
    //         alert("업로드 성공!!");
    //     }
    // });
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

