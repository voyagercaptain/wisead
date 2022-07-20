
    // 공백이 있나 없나 체크
    function checkSpace(str) {
            if(str.search(/\s/) != -1) {
            return true;
        } else {
            return false;
        }
    }

    // 특수 문자가 있나 없나 체크
    function checkSpecial(str) {
        var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

        if(special_pattern.test(str) == true) {
            return true;
        } else {
            return false;
        }
    }
    
    //대소문자 구분
    function checkCaseSensitive(str) {
        var pattern = /[A-Z]/;			// 문자
        if(!pattern.test(str)) {
            return false;
        } else {
            return true;
        }
    }

    //영문약어명 체크
    function checkAbbreviation(str) {
        var pattern = /[A-Z_]/;			// 문자
        if(!pattern.test(str)) {
            return false;
        } else {
            return true;
        }
    }