import PropType from 'prop-types';
import styles from './Chat.module.css';
import axios from 'axios';
import { useRecoilValue, useResetRecoilState, useSetRecoilState } from 'recoil';
import Profile from '../../recoil/profile';
import ModalState from '../../recoil/modal.js';

const Chat = ({ message, tutoringId, getMessageRoom }) => {
  const { senderId, messageContent, senderName, createAt } = message;
  const { profileId } = useRecoilValue(Profile);

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  // 매칭 요청 승인 API
  const confirmMatching = async () => {
    await axios
      .post(`/tutoring/details/${profileId}/${tutoringId}`)
      .then((res) => {
        if (res.data.data.tutoringStatus === 'PROGRESS') {
          window.location.href('/tutoring');
        }
      })
      .catch((err) => console.log(err));
  };

  // 매칭 요청중인 과외 (완전 삭제) API
  const deleteTutoring = async () => {
    await axios
      .delete(`/tutoring/details/${tutoringId}`)
      .then(() => {
        console.log('요청취소 -> 특정과외삭제');
      })
      .catch((err) => console.log(err));
  };

  // 요청 수락 버튼
  const matchConfirmModal = {
    isOpen: true,
    modalType: 'confirm',
    props: {
      text: `요청 수락 시 매칭이 완료됩니다.
    매칭 요청을 수락 하시겠습니까?
    `,
      modalHandler: () => {
        confirmMatching();
        resetModal();
        getMessageRoom();
        setModal(matchAlertModal);
      },
    },
  };

  const matchAlertModal = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: `매칭 완료되었습니다.
    생성된 과외는 과외 관리 페이지에서
    확인하실 수 있습니다.`,
    },
  };

  //요청 취소 버튼
  const cancelConfirmModal = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `매칭 요청을 취소하시겠습니까?    
    `,
      modalHandler: () => {
        deleteTutoring();
        resetModal();
        getMessageRoom();
        setModal(cancelAlertModal);
      },
    },
  };

  const cancelAlertModal = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: `매칭 요청 취소가 완료되었습니다.`,
    },
  };

  return (
    <div
      className={`${styles.chatContainer} ${
        senderId === profileId ? styles.sendChat : undefined
      }`}
    >
      {senderId === profileId ? undefined : <h5>{senderName}</h5>}
      {messageContent === 'REQ_UEST' ? (
        senderId === profileId ? (
          <div className={styles.matchingBox}>
            <p>매칭 요청을 보냈습니다.</p>
            <button
              className={styles.requestCancelBtn}
              onClick={() => setModal(cancelConfirmModal)}
            >
              요청 취소하기
            </button>
          </div>
        ) : (
          <div className={styles.matchingBox}>
            <p>매칭 요청이 도착했습니다.</p>
            <button
              className={styles.checkRequestBtn}
              onClick={() => setModal(matchConfirmModal)}
            >
              요청 확인하기
            </button>
          </div>
        )
      ) : (
        <p className={styles.text}>{messageContent}</p>
      )}
      {createAt && (
        <span className={styles.time}>
          {Number(createAt.slice(11, 13)) >= 12 ? 'PM' : 'AM'}
          {createAt.slice(11, 16)}
        </span>
      )}
    </div>
  );
};

Chat.propTypes = {
  message: PropType.object,
  tutoringId: PropType.number,
  getMessageRoom: PropType.func,
};

export default Chat;
