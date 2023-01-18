import styles from './MessageContent.module.css';
import { MdMenu } from 'react-icons/md';
import PropType from 'prop-types';
import { useState, useEffect } from 'react';
import Chat from './Chat';
import { useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import axios from 'axios';

const MessageContent = ({
  messageRoom,
  currentRoomId,
  delMessageRoom,
  headers,
  profile,
  setMessageRoom,
}) => {
  const { tutorId, tuteeId, messages } = messageRoom;
  const [tutoringTitle, setTutoringTitle] = useState('');
  const [isMenu, setIsMenu] = useState(false);
  const [inputValue, setInputValue] = useState('');

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const [myProfileId, setIsMyProfileId] = useState(tuteeId);
  const [yourProfileId, setYourProfileId] = useState(tutorId);

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
      .post(
        `${process.env.REACT_APP_BASE_URL}/messages`,
        {
          senderId: myProfileId,
          receiverId: yourProfileId,
          messageRoomId: currentRoomId,
          messageContent: inputValue,
        },
        {
          headers: headers,
        }
      )
      .then(() => {
        console.log('메세지 전송');
        //메세지 페이지 새로고침  or MessageRoom 부분만 따로 재 랜더
      })
      .catch((err) => console.log(err));
  };

  const data = JSON.stringify({
    tutorId: tutorId,
    tuteeId: tuteeId,
    tutoringTitle: tutoringTitle,
    messageRoomId: currentRoomId,
  });

  const createTutoring = async () => {
    await axios
      .post(
        `${process.env.REACT_APP_BASE_URL}/tutoring/${messages[0].senderId}`,
        data,
        {
          headers: headers,
        }
      )
      .then(() => {
        console.log('메세지 전송');
      })
      .catch((err) => console.log(err));
  };

  const matchConfirmProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: `상대방의 요청 수락 시 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,

      modalHandler: (_, value) => {
        console.log(value, 'value');
        setTutoringTitle(value);
        //TODO: 매칭 요청 후 매칭 요청을 보냈습니다 보이기

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
            authId={profile.profileId}
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
            }
          }}
        />
        <button onClick={sendMessage} className={styles.sendBtn}>
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
  setMessageRoom: PropType.func,
  headers: PropType.object,
  currentRoomId: PropType.number,
  profile: PropType.object,
};

export default MessageContent;
