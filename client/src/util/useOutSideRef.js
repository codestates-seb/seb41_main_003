import { useState, useEffect, useRef } from 'react';

const useOutSideRef = (menuRef) => {
  const ref = useRef(null);
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (ref.current && !menuRef.current.contains(event.target)) {
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
