import styles from './MessageContent.module.css';
import { MdMenu } from 'react-icons/md';
import PropType from 'prop-types';
import { useState, useEffect } from 'react';
import Chat from './Chat';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import ModalState from '../../recoil/modal.js';
import axios from 'axios';
import CurrentRoomIdState from '../../recoil/currentRoomId';
import Profile from '../../recoil/profile';
import reIssueToken from '../../util/reIssueToken';

const MessageContent = ({ messageRoom, delMessageRoom, getMessageRoom }) => {
  const { tutorId, tuteeId, messages } = messageRoom;

  const { profileId } = useRecoilValue(Profile);
  const [isMenu, setIsMenu] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [tutoringTitle, setTutoringTitle] = useState('');
  const [receiveMessageId, setReceiveMessageId] = useState(0);
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);

  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const [tutoringId, setTutoringId] = useState(0);

  useEffect(() => {
    if (profileId === tuteeId) {
      setReceiveMessageId(tutorId);
    } else setReceiveMessageId(tuteeId);
  }, [CurrentRoomId]);

  // 메세지 post API
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
        getMessageRoom();
      })
      .catch((err) => {
        if (err.data.message === 'ERR_BAD_REQUEST') {
          return <div>메세지를 입력하세요</div>;
        }
      });
  };

  // 과외 생성 API
  const createTutoringAPI = async () => {
    await axios
      .post(`/tutoring/${profileId}`, {
        tutorId: tutorId,
        tuteeId: tuteeId,
        tutoringTitle: tutoringTitle,
        messageRoomId: CurrentRoomId,
      })
      .then((res) => {
        console.log('과외 생성');
        console.log(res.data.data.tutoringI);
        setTutoringId(res.data.data.tutoringId);
      })
      .catch((err) => console.log(err));
  };
  console.log(tutoringId);
  async function donggi(val) {
    await setTutoringTitle(val);
  }

  // 매칭 요청 버튼 모달
  const matchConfirmProps = {
    isOpen: true,
    modalType: 'confirmText',
    props: {
      text: `상대방의 요청 수락 시 매칭이 완료됩니다.
    매칭 요청 하시겠습니까?

    매칭을 원하신다면 과외의 이름을 작성해주세요.
    `,
      modalHandler: (_, value) => {
        // donggi(value).then();
        setTutoringTitle(value);
        resetModal();
        setModal(matchAlertProps);
        console.log(tutoringTitle, 'confirmText');
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
        createTutoringAPI();
        console.log(tutoringTitle, '과외제목 alertModal');
        resetModal();
        getMessageRoom();
        // window.location.href = `/message/${profileId}`;
      },
    },
  };

  // 상담 취소 버튼 모달
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
        window.location.href = `/message/${profileId}`;
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
            key={message.messageId}
            tutoringId={tutoringId}
            getMessageRoom={getMessageRoom}
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
