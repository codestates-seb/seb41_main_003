import styles from './MessageContent.module.css';
import { MdMenu, MdRefresh } from 'react-icons/md';
import PropType from 'prop-types';
import { useState, useEffect, useRef } from 'react';
import Chat from './Chat';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import axios from 'axios';
import CurrentRoomIdState from '../../recoil/currentRoomId';
import Profile from '../../recoil/profile';
import useOutSideRef from '../../util/useOutSideRef';

const MessageContent = ({
  messageRoom,
  delMessageRoom,
  getMessageRoom,
  getMessageList,
}) => {
  const { tutorId, tuteeId, tutoringId, messages } = messageRoom;
  const menuRef = useRef(null);
  const [dropdownRef, isMenu, setIsMenu] = useOutSideRef(menuRef);
  const [inputValue, setInputValue] = useState('');
  const [receiveMessageId, setReceiveMessageId] = useState(0);
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
  const { profileId } = useRecoilValue(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);
  const scrollRef = useRef();

  //* 채팅창의 스크롤 위치 제어
  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  }, [messageRoom]);

  //*
  const refreshMessage = () => {
    getMessageList();
    getMessageRoom();
  };
  //* messageList가 변경될때마다 message의 receiver를 변경해줌
  const setReceiver = () => {
    if (profileId === tuteeId) {
      setReceiveMessageId(tutorId);
    } else setReceiveMessageId(tuteeId);
  };

  useEffect(() => {
    setReceiver();
  }, [messageRoom]);

  //* 메세지 post API
  const sendMessage = async () => {
    await axios
      .post(`/messages`, {
        senderId: profileId,
        receiverId: receiveMessageId,
        messageRoomId: CurrentRoomId,
        messageContent: inputValue,
      })
      .then(() => {
        console.log('메세지 전송');
        refreshMessage();
      })
      .catch((err) => console.log(err));
  };

  //* 매칭 요청 (과외 생성) API
  const createTutoringAPI = async (value) => {
    await axios
      .post(`/tutoring/${profileId}`, {
        tutorId: tutorId,
        tuteeId: tuteeId,
        tutoringTitle: value,
        messageRoomId: CurrentRoomId,
      })
      .then((res) => {
        setModal(matchAlertProps);
        console.log(res.data.data.tutoringId, '과외 생성 API');
      })
      .catch((err) => {
        if (err.message === 'Request failed with status code 409') {
          setModal(alreadyMatchModal);
        }
        console.log(err, '매칭 요청');
      });
  };

  //* 매칭 중복 요청 오류 모달
  const alreadyMatchModal = {
    isOpen: true,
    modalType: 'alert',
    props: {
      text: `이미 진행중인 매칭 요청 이나 과외가 있습니다.`,
    },
  };

  //* 매칭 요청 모달
  const matchConfirmProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: `상대방의 요청 수락 후에 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,
      modalHandler: (_, value) => {
        createTutoringAPI(value);
        resetModal();
      },
      placeHolder: '과외의 이름을 작성하세요',
    },
  };
  const matchAlertProps = {
    isOpen: true,
    modalType: 'handlerAlert',
    props: {
      text: `매칭 요청이 완료되었습니다.
    상대방의 요청 수락 이후에는 과외 관리 페이지에서
    확인하실 수 있습니다.`,
      modalHandler: () => {
        resetModal();
        refreshMessage();
      },
    },
  };

  //* 상담 취소 버튼 모달
  const cancelConfirmProps = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `상담 취소 하시겠습니까?
      상담 취소 시 대화 내역이 모두 삭제됩니다.`,
      modalHandler: () => {
        console.log('상담 취소');
        delMessageRoom();
        resetModal();
        setModal(cancelAlertProps);
      },
    },
  };
  const cancelAlertProps = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: `상담이 취소되었습니다.`,
      modalHandler: () => {
        window.location.href = `/message`;
      },
    },
  };

  return (
    <div className={styles.container}>
      <div className={styles.messageContainer} ref={scrollRef}>
        <button className={styles.refresh} onClick={() => refreshMessage()}>
          <MdRefresh size={24} />
        </button>
        {messages.map((message) => (
          <Chat
            message={message}
            key={message.messageId}
            getMessageRoom={getMessageRoom}
            receiveMessageId={receiveMessageId}
            CurrentRoomId={CurrentRoomId}
            tutoringId={tutoringId}
          />
        ))}
      </div>
      <div className={styles.sendContainer}>
        <button
          className={styles.menu}
          onClick={() => setIsMenu((prev) => !prev)}
          ref={menuRef}
        >
          <MdMenu />
        </button>
        <input
          className={styles.textInput}
          id="sendMsg"
          placeholder="메세지를 입력하세요"
          type="text"
          value={inputValue}
          onChange={(e) => setInputValue(e.target.value)}
          onKeyUp={(e) => {
            if (e.key === 'Enter' && e.target.value) {
              sendMessage();
              setInputValue('');
            }
          }}
        />
        <button
          onClick={() => {
            if (inputValue !== '') {
              sendMessage();
              setInputValue('');
            }
          }}
          className={styles.sendBtn}
        >
          전송
        </button>
        {/* dropDown */}
        {isMenu && (
          <div className={styles.dropdown} ref={dropdownRef}>
            <button onClick={() => setModal(matchConfirmProps)}>
              매칭 요청
            </button>
            <button onClick={() => setModal(cancelConfirmProps)}>
              상담 취소
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

MessageContent.propTypes = {
  messageRoom: PropType.object,
  delMessageRoom: PropType.func,
  getMessageRoom: PropType.func,
  getMessageList: PropType.func,
};

export default MessageContent;
