import styles from './DropDown.module.css';
import { useState } from 'react';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { MdMenu, MdClose } from 'react-icons/md';

const DropDown = () => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const [isClicked, setIsClicked] = useState(false);

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '일지 수정 페이지로 이동 하시겠습니까?',
      modalHandler: () => {
        console.log('일지 수정 페이지로 이동');
        //TODO: 일지 수정 페이지로 이동
        resetModal();
      },
    },
  };

  const cancel = {
    isOpen: true,
    modalType: 'cancelConfirm',
    props: {
      text: '삭제 하시겠습니까? 삭제한 일지는 되돌릴 수 없습니다.',
      modalHandler: () => {
        console.log('삭제 취소 확인 버튼');
        //TODO: 과외 리스트 페이지로 리다이렉션
        resetModal();
      },
    },
  };

  return (
    <div className={styles.dropDownButton}>
      <button id="dropDown" onClick={() => setIsClicked((prev) => !prev)}>
        <div className={styles.buttonImg}>
          {isClicked ? <MdClose size="24px" /> : <MdMenu size="24px" />}
        </div>
      </button>
      {isClicked && (
        <ul>
          <li>
            <ButtonNightBlue
              text="일지 수정"
              buttonHandler={() => setModal(confirm)}
            />
          </li>
          <li>
            <ButtonRed
              text="일지 삭제"
              buttonHandler={() => setModal(cancel)}
            />
          </li>
        </ul>
      )}
    </div>
  );
};

export default DropDown;
