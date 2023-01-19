import { useEffect, useRef } from 'react';

/**
 * 기존 useEffect와 달리 최초 렌더링 시에는 실행되지 않습니다.
 * @param {function} func 종속성 배열의 값이 변화할 때 동작할 콜백함수
 * @param {array} deps 종속성 배열
 */
const useDidMountEffect = (func, deps) => {
  const didMount = useRef(false);

  useEffect(() => {
    if (didMount.current) func();
    else didMount.current = true;
  }, deps);
};

export default useDidMountEffect;
