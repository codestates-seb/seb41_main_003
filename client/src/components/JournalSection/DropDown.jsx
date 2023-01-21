import styles from './DropDown.module.css';
import { useState } from 'react';
import { ButtonNightBlue, ButtonRed } from '../Button';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal';
import { MdMenu, MdClose } from 'react-icons/md';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

const DropDown = () => {
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const navigate = useNavigate();
  const { dateNoticeId } = useParams();

  const [isClicked, setIsClicked] = useState(false);

  const confirm = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: '일지 수정 페이지로 이동 하시겠습니까?',
      modalHandler: () => {
        console.log('일지 수정페이지로 이동');
        resetModal();
        navigate(`/editjournal/${dateNoticeId}`);
      },
    },
  };

  const deleteHandler = async () => {
    axios
      .delete(`/tutoring/date-notice/${dateNoticeId}`)
      .then(() => console.log('삭제완료'))
      .catch((err) => console.log(err));
  };

  const deleteModal = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `삭제 하시겠습니까?
      삭제한 일지는 되돌릴 수 없습니다.`,
      modalHandler: () => {
        deleteHandler();
        console.log('일지 삭제');
        setModal(alertModal);
      },
    },
  };

  const alertModal = {
    isOpen: true,
    modalType: 'handlerAlert',
    props: {
      text: '일지가 삭제 되었습니다.',
      modalHandler: () => {
        // TODO : 튜터링 페이지로 이동 필요
        navigate(`/tutoring`);
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
              buttonHandler={() => setModal(deleteModal)}
            />
          </li>
        </ul>
      )}
    </div>
  );
};

export default DropDown;
