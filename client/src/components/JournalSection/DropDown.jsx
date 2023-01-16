import styles from './DropDown.module.css';
import { useState } from 'react';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { MdMenu, MdClose } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

const DropDown = () => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const Navigate = useNavigate();

  const [isClicked, setIsClicked] = useState(false);

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '일지 수정 페이지로 이동 하시겠습니까?',
      modalHandler: () => {
        //TODO: 해당 dateNoticeId의 일지 수정 페이지로 이동 (useParam)
        console.log('일지 수정페이지로 이동');
        Navigate('/editjournal');
        resetModal();
      },
    },
  };

  const cancel = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `삭제 하시겠습니까?
      삭제한 일지는 되돌릴 수 없습니다.`,
      modalHandler: () => {
        //TODO: 서버에 일지 삭제 요청
        //TODO: 해당 프로필Id의 과외 리스트 페이지로 이동 (useParam)
        console.log('일지 삭제');
        resetModal();
        setModal(redAlertModal);
        Navigate(`/tutoring`);
      },
    },
  };

  const redAlertModal = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: '일지가 삭제 되었습니다.',
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
