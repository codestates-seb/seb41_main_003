import { useEffect, useState } from 'react';

const defaultOptions = {
  root: null,
  rootMargin: '1px',
  threshold: '1',
};

/**
 * 무한 스크롤 구현을 위한 커스텀 훅
 * @param {function} callbackFunc 특정 요소가 보일 때 작동할 콜백 함수
 * @param {ref} ref 콜백 함수를 동작시킬 요소 (ref)
 * @param {object} options InterSectionObserver 옵션
 */
const useScroll = (callbackFunc, ref, options = defaultOptions) => {
  const [isShow, setIsShow] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const intersectionFunc = (entries) => {
    entries.forEach((entry) => {
      //* 요소가 보이는지에 따라 상태 변경
      if (entry.isIntersecting) {
        setIsShow(true);
      } else {
        setIsShow(false);
      }
    });
  };

  useEffect(() => {
    // IntersectionObserver 생성
    const element = ref.current;

    const io = new IntersectionObserver(intersectionFunc, options);
    io.observe(element);
    return () => io?.disconnect(element);
  }, []);

  //* 상태가 true일 때 콜백함수 실행
  useEffect(() => {
    if (!isShow) return;
    setIsLoading(true);
    callbackFunc();
  }, [isShow]);

  return [isLoading, setIsLoading];
};

export default useScroll;
