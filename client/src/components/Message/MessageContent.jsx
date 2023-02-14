import styles from './MessageContent.module.css';
import { MdClose, MdMenu, MdRefresh } from 'react-icons/md';
import PropType from 'prop-types';
import { useEffect, useRef } from 'react';
import Chat from './Chat';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import axios from 'axios';
import CurrentRoomIdState from '../../recoil/currentRoomId';
import Profile from '../../recoil/profile';
import useOutSideRef from '../../util/useOutSideRef';
import dayjs from 'dayjs';
import useLiveChat from '../../util/useLiveChat';

const MessageContent = ({ delMessageRoom, getMessageList, setIsChat }) => {
  const { publish, messageRoom, setMessageRoom, text, setText } = useLiveChat();
  const { tutorId, tuteeId, tutoringId, messages } = messageRoom;
  const menuRef = useRef(null);
  const scrollRef = useRef(null);
  const inputRef = useRef(null);
  const [dropdownRef, isMenu, setIsMenu] = useOutSideRef(menuRef);
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
  const { profileId } = useRecoilValue(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  //* 채팅창의 스크롤 위치 제어
  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
    inputRef.current.focus();
  }, [messageRoom]);

  const getMessageRoom = async () => {
    if (CurrentRoomId !== 0 && CurrentRoomId !== undefined)
      await axios
        .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
        .then(({ data: data }) => {
          setMessageRoom(data);
        })
        .catch((err) => console.log(err));
  };

  //*
  const refreshMessage = () => {
    getMessageList();
    getMessageRoom();
  };

  //* 메세지 post API
  const sendMessage = () => {
    publish();
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
      .then(() => {
        setModal(matchAlertProps);
      })
      .catch((err) => {
        console.log(err);
        if (err.message === 'Request failed with status code 409') {
          setModal(alreadyMatchModal);
        }
      });
  };

  //* 매칭 중복 요청 오류 모달
  const alreadyMatchModal = {
    isOpen: true,
    modalType: 'redAlert',
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
        delMessageRoom();
      },
    },
  };

  return (
    <div className={styles.container}>
      <div className={styles.messageContainer} ref={scrollRef}>
        <button
          className={`${styles.btn} ${styles.close} `}
          onClick={() => {
            setIsChat(false);
          }}
        >
          <MdClose />
        </button>

        <button
          className={`${styles.btn} ${styles.refresh} `}
          onClick={() => refreshMessage()}
        >
          <span>새로고침</span>
          <MdRefresh />
        </button>

        {messages?.map((message, idx) => {
          const prevDate = dayjs(messages[idx - 1]?.createAt).format(
            'YYYY년 MM월 DD일'
          );
          const currDate = dayjs(message?.createAt).format('YYYY년 MM월 DD일');

          return (
            <>
              {prevDate !== currDate && (
                <p className={styles.dateLine}>{currDate}</p>
              )}
              <Chat
                publish={publish}
                message={message}
                key={`msg${message.messageId}`}
                tutoringId={tutoringId}
              />
            </>
          );
        })}
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
          value={text}
          onChange={(e) => setText(e.target.value)}
          onKeyUp={(e) => {
            if (e.key === 'Enter' && e.target.value) {
              sendMessage();
              setText('');
            }
          }}
          ref={inputRef}
          disabled={CurrentRoomId === 0 ? true : false}
        />
        <button
          onClick={() => {
            if (text !== '') {
              sendMessage();
              setText('');
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
  setIsChat: PropType.func,
};

export default MessageContent;
