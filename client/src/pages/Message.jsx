import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import {
  useRecoilValue,
  useRecoilState,
  useSetRecoilState,
  useResetRecoilState,
} from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId.js';
import ModalState from '../recoil/modal.js';
import { useNavigate } from 'react-router-dom';
import Profile from '../recoil/profile';

const Message = () => {
  const [messageList, setMessageList] = useState([0]);

  const [isChat, setIsChat] = useState(false);
  const [pageInfo, setPageInfo] = useState({});
  const [CurrentRoomId, resetCurrentRoomId] =
    useRecoilState(CurrentRoomIdState);
  const { profileId } = useRecoilValue(Profile);
  const setModal = useSetRecoilState(ModalState);
  const resetModal = useResetRecoilState(ModalState);

  const navigate = useNavigate();

  const noListAlertModal = {
    isOpen: true,
    modalType: 'handlerAlert',
    props: {
      text: `대화상대가 없습니다.

      ${
        sessionStorage.getItem('userStatus') === 'TUTOR' ? '튜티' : '튜터'
      } 프로필의 문의하기 버튼을 눌러서 대화를 시작해주세요.`,
      modalHandler: () => {
        navigate(-1);
        resetModal();
      },
    },
  };

  const getMessageList = async () => {
    await axios
      .get(`/messages/${profileId}`)
      .then((res) => {
        setMessageList(res.data.data);
        setPageInfo(res.data.pageInfo);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getMessageList();
    window.addEventListener('resize', () => {
      if (window.innerWidth > 768) setIsChat(false);
    });
    return () => {
      resetCurrentRoomId();
      window.removeEventListener('resize', () => {
        if (window.innerWidth > 768) setIsChat(false);
      });
    };
  }, []);

  useEffect(() => {
    if (messageList.length === 0) setModal(noListAlertModal);
  }, [messageList]);

  // useEffect(() => {
  //   getMessageRoom();
  // }, [CurrentRoomId]);

  //대화 화면 조회 API
  // const getMessageRoom = async () => {
  //   if (CurrentRoomId !== 0 && CurrentRoomId !== undefined)
  //     await axios
  //       .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
  //       .then((res) => {
  //         setMessageRoom(res.data.data);
  //       })
  //       .catch((err) => console.log(err));
  // };

  const cancelAlertProps = {
    isOpen: true,
    modalType: 'redAlert',
    props: {
      text: `상담이 취소되었습니다.`,
      modalHandler: () => {
        location.reload();
      },
    },
  };

  const delMessageRoom = async () => {
    await axios
      .delete(`/messages/rooms/${CurrentRoomId}`)
      .then(() => {
        setModal(cancelAlertProps);
      })
      .catch(() => {
        setModal({
          isOpen: true,
          modalType: 'redAlert',
          props: {
            text: '대화 상대와 매칭중인 과외가 있다면\n상담 취소를 할 수 없습니다.',
          },
        });
      });
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <div className={styles.message}>
          <MessageList
            messageList={messageList}
            pageInfo={pageInfo}
            setMessageList={setMessageList}
            setPageInfo={setPageInfo}
            setIsChat={setIsChat}
          />
          <div className={`${styles.chatContainer} ${isChat && styles.active}`}>
            {CurrentRoomId === 0 || CurrentRoomId === undefined ? (
              <div className={styles.initialContain}>
                대화 상대를 선택해주세요.
              </div>
            ) : (
              <MessageContent
                getMessageList={getMessageList}
                delMessageRoom={delMessageRoom}
                setIsChat={setIsChat}
              />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Message;
