import styles from './MessageContent.module.css';
import { MdMenu } from 'react-icons/md';
import PropType from 'prop-types';
import { useState, useEffect } from 'react';
import Chat from './Chat';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import axios from 'axios';
import CurrentRoomIdState from '../../recoil/currentRoomId';
import TutoringTitleState from '../../recoil/tutoringTitle';
import Profile from '../../recoil/profile';

const MessageContent = ({ messageRoom, delMessageRoom, getMessageRoom }) => {
  const { tutorId, tuteeId, messages } = messageRoom;
  const [isMenu, setIsMenu] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [myProfileId, setIsMyProfileId] = useState(tuteeId);
  const [yourProfileId, setYourProfileId] = useState(tutorId);
  const profile = useRecoilValue(Profile);
  const tutoringTitle = useRecoilValue(TutoringTitleState);
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const amITutee = () => {
    if (profile.profileId !== tuteeId) {
      setIsMyProfileId(tutorId);
      setYourProfileId(tuteeId);
    }
  };

  useEffect(() => {
    amITutee();
  }, []);

  // 메세지 post API
  const sendMessage = async () => {
    await axios
      .post(`${process.env.REACT_APP_BASE_URL}/messages`, {
        senderId: myProfileId,
        receiverId: yourProfileId,
        messageRoomId: CurrentRoomId,
        messageContent: inputValue,
      })
      .then(() => {
        console.log('메세지 전송');
        getMessageRoom();
      })
      .catch((err) => console.log(err));
  };

  const sendReq_est = async () => {
    await axios
      .post(`${process.env.REACT_APP_BASE_URL}/messages`, {
        senderId: myProfileId,
        receiverId: yourProfileId,
        messageRoomId: CurrentRoomId,
        messageContent: 'REQ_UEST',
      })
      .then(() => {
        console.log('REQ_UEST');
        getMessageRoom();
      })
      .catch((err) => console.log(err));
  };

  const createTutoring = async () => {
    await axios
      .post(`${process.env.REACT_APP_BASE_URL}/tutoring/${myProfileId}`, {
        tutorId: tutorId,
        tuteeId: tuteeId,
        tutoringTitle: tutoringTitle,
        messageRoomId: CurrentRoomId,
      })
      .then(() => {
        console.log('메세지 전송');
      })
      .catch((err) => console.log(err));
  };

  const matchConfirmProps = {
    isOpen: true,
    modalType: 'getText',
    props: {
      text: `상대방의 요청 수락 시 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,

      modalHandler: () => {
        sendReq_est();
        resetModal();
        setModal(matchAlertProps);
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
        createTutoring();
        console.log(tutoringTitle, 'alertModal');
        resetModal();
      },
    },
  };

  const cancelConfirmProps = {
    isOpen: true,
    modalType: 'redConfirm',
    props: {
      text: `상담 취소 하시겠습니까?
      상담 취소 시 대화 내역이 모두 삭제됩니다.`,
      modalHandler: () => {
        delMessageRoom();
        console.log('상담 취소');
        resetModal();
        setModal(cancelAlertProps);
        location.reload();
      },
    },
  };

  const cancelAlertProps = {
    isOpen: true,
    modalType: 'redAlert',
    props: { text: `상담이 취소되었습니다.` },
  };

  return (
    <div className={styles.container}>
      <div className={styles.messageContainer}>
        {messages.map((message) => (
          <Chat
            message={message}
            authId={myProfileId}
            key={message.messageId}
          />
        ))}
      </div>
      <div className={styles.sendContainer}>
        <button
          className={styles.menu}
          onClick={() => setIsMenu((prev) => !prev)}
          onBlur={() => {
            setTimeout(() => setIsMenu(false), 100);
          }}
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
              console.log('11');
            }
          }}
        />
        <button
          onClick={() => {
            sendMessage();
            setInputValue('');
          }}
          className={styles.sendBtn}
        >
          전송
        </button>
        {/* dropDown */}
        {isMenu && (
          <div className={styles.dropdown}>
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
  profile: PropType.object,
};

export default MessageContent;
