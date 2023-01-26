import { useState, useEffect, useRef } from 'react';

const useOutSideRef = (menuRef) => {
  const ref = useRef(null);
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    const childNode = Array.prototype.slice.call(menuRef.current.children);

    const handleClickOutside = (event) => {
      if (
        ref.current &&
        !ref.current.contains(event.target) &&
        event.target !== menuRef.current &&
        !childNode.includes(event.target)
      ) {
        setIsActive(!isActive);
      }
    };
    if (isActive) {
      document.addEventListener('click', handleClickOutside);
    }

    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, [isActive, ref]);

  return [ref, isActive, setIsActive];
};

export default useOutSideRef;
