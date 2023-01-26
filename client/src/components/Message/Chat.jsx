import PropType from 'prop-types';
import styles from './Chat.module.css';
import axios from 'axios';
import { useResetRecoilState, useSetRecoilState, useRecoilValue } from 'recoil';
import Profile from '../../recoil/profile';
import ModalState from '../../recoil/modal.js';

const Chat = ({
  message,
  getMessageRoom,
  CurrentRoomId,
  receiveMessageId,
  tutoringId,
}) => {
  const { senderId, messageContent, senderName, createAt } = message;
  const { profileId } = useRecoilValue(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  // 매칭 요청 승인 API
  const confirmMatching = async () => {
    await axios
      .patch(`/tutoring/details/${profileId}/${tutoringId}`)
      .then((res) => {
        console.log(res, '매칭요청 승인');
        matchingConfirmMessage();
        setModal(matchAlertModal);
      })
      .catch((err) => {
        if (err.message === 'Request failed with status code 400') {
          setModal(alreadyConfirmedModal);
        }
        console.log(err, '매칭요청 승인');
      });
  };

  //* 매칭 요청 취소 (과외 삭제) API
  const deleteTutoring = async () => {
    await axios
      .delete(`/tutoring/details/${tutoringId}`)
      .then((res) => {
        console.log(res, tutoringId, '매칭요청 취소');
        matchingCancelessage();
        setModal(cancelAlertModal);
      })
      .catch((err) => {
        if (err.message === 'Request failed with status code 400') {
          setModal(alreadyCanceledMatchModal);
        }
        console.log(err, '매칭요청 취소');
      });
  };

  //* 매칭요청 승인시에 보내는 메세지
  const matchingConfirmMessage = async () => {
    await axios
      .post(`/messages`, {
        senderId: profileId,
        receiverId: receiveMessageId,
        messageRoomId: CurrentRoomId,
        messageContent: 'MAT_CHING_CON_FIRM',
      })
      .then(() => {
        console.log('메세지 전송');
        getMessageRoom();
      })
      .catch((err) => console.log(err));
  };

  //* 매칭 취소시에 보내는 메세지
  const matchingCancelessage = async () => {
    await axios
      .post(`/messages`, {
        senderId: profileId,
        receiverId: receiveMessageId,
        messageRoomId: CurrentRoomId,
        messageContent: 'MAT_CHING_CAN_CEL',
      })
      .then(() => {
        console.log('메세지 전송');
        getMessageRoom();
      })
      .catch((err) => console.log(err));
  };

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

  //* 매칭 중복 수락 오류 모달
  const alreadyConfirmedModal = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: `이미 수락된 매칭 입니다.
          
          과외 관리 메뉴의 과외 리스트를 확인해주세요.`,
    },
  };

  //* 매칭 취소 요청 오류 모달
  const alreadyCanceledMatchModal = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: `이미 취소된 요청입니다.`,
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
      ) : messageContent === 'MAT_CHING_CON_FIRM' ? (
        <div className={styles.matchingComent}>
          <p>매칭 요청이 승인 되었습니다.</p>
        </div>
      ) : messageContent === 'MAT_CHING_CAN_CEL' ? (
        <div className={styles.matchingComent}>
          <p>매칭 요청이 취소 되었습니다.</p>
        </div>
      ) : (
        <p className={styles.text}>{messageContent}</p>
      )}
      {createAt && (
        <span className={styles.time}>
          {Number(createAt.slice(11, 13)) >= 12 ? 'PM' : 'AM'}{' '}
          {createAt.slice(11, 16)}
        </span>
      )}
    </div>
  );
};

Chat.propTypes = {
  message: PropType.object,
  receiveMessageId: PropType.string,
  CurrentRoomId: PropType.string,
  tutoringId: PropType.number,
  getMessageRoom: PropType.func,
};

export default Chat;
