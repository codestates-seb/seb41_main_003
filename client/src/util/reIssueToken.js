import axios from 'axios';

axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

axios.defaults.headers.common['Authorization'] =
  sessionStorage.getItem('authorization') ||
  localStorage.getItem('authorization');

/**
 * 토큰 재발급을 위한 함수입니다.
 * 조건문을 통해 403 에러일 시에만 해당 함수를 호출하시기 바랍니다.
 * 토큰 재발급에 실패하면 로컬과 세션에 인증과 관련된 모든 정보를 삭제합니다.
 * 토큰 재발급 실패 시 Profile 상태 및 리다이렉션은 해당 함수에 catch를 추가적으로 직접 작성해야합니다.
 * @param {Function} func 토큰 재발급 이후 실행 할 콜백 함수입니다.
 */
const reIssueToken = (func) => {
  return new Promise((resolve, reject) => {
    console.log('token');
    axios
      .get(
        `/auth/reissue-token/${
          sessionStorage.getItem('userId') || localStorage.getItem('userId')
        }`
      )
      .then(({ data }) => {
        if (localStorage.getItem('userId')) {
          localStorage.setItem('authorization', data.Authorization);
          localStorage.setItem('userId', data.userId);
          localStorage.setItem('userStatus', data.userStatus);
        } else if (sessionStorage.getItem('userId')) {
          sessionStorage.setItem('authorization', data.Authorization);
          sessionStorage.setItem('userId', data.userId);
          sessionStorage.setItem('userStatus', data.userStatus);
        }
        func();
        resolve(data);
      })
      .catch((error) => {
        localStorage.clear();
        sessionStorage.clear();
        reject(error);
      });
  });
};

export default reIssueToken;
