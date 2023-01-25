import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilValue, useSetRecoilState, useResetRecoilState } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId.js';
<<<<<<< Updated upstream
import ModalState from '../recoil/modal.js';
import { useNavigate } from 'react-router-dom';
import Profile from '../recoil/profile';

const Message = () => {
  const [messageList, setMessageList] = useState([0]);
  const [messageRoom, setMessageRoom] = useState({
    messages: [
      {
        messageContent: '대화를 선택해주세요',
      },
    ],
  });
  const [pageInfo, setPageInfo] = useState({});
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
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
        console.log(res.data.data, '메세지 리스트 API');
      })
      .catch((err) => console.log(err, 'getMessageList'));
  };
=======
import Profile from '../recoil/profile';
// import reIssueToken from '../util/reIssueToken.js';

const initialState = {
  messageRoomId: 0,
  messages: [
    {
      messageContent: '대화를 선택해주세요..',
      createAt: '2023-01-10T10:53:13.9958657',
    },
  ],
  createAt: '2023-01-10T10:53:13.9958657',
};

const Message = () => {
  const profile = useRecoilValue(Profile);
  const [CurrentRoomId, setCurrentRoomId] = useRecoilState(CurrentRoomIdState);
  const [messageList, setMessageList] = useState([]);
  const [messageRoom, setMessageRoom] = useState(initialState);
>>>>>>> Stashed changes

  useEffect(() => {
    getMessageList();
  }, []);

<<<<<<< Updated upstream
  useEffect(() => {
    if (messageList.length !== 0) getMessageRoom();
    else {
      setModal(noListAlertModal);
    }
  }, [messageList]);
=======
  //프로필 메세지 방 리스트 조회 API
  const getMessageList = async () => {
    await axios.get(`/messages/${profile.profileId}`).then((res) => {
      setMessageList(res.data.data);
      console.log('getMessageList API');
    });
    // .catch(({ response }) => {
    //   if (response.data.message === 'EXPIRED ACCESS TOKEN') {
    //     reIssueToken(getMessageList).catch(() => {
    //       console.log(response, 'getMessageList 403 error');
    //       window.location.href = '/login';
    //     });
    //   }
    // });
  };
>>>>>>> Stashed changes

  useEffect(() => {
    getMessageRoom();
  }, [CurrentRoomId]);

  //대화 화면 조회 API
  const getMessageRoom = async () => {
    await axios
<<<<<<< Updated upstream
      .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log(res.data.data, 'MessageRoom API');
      })
      .catch((err) => console.log(err, 'getMessageRoom'));
  };

  const delMessageRoom = async () => {
    await axios
      .delete(`/messages/rooms/${CurrentRoomId}`)
      .then(() => {
        window.location.ref('/message');
        console.log('현재 대화방 삭제');
      })
      .catch((err) => console.log(err));
=======
      .get(`/messages/rooms/${profile.profileId}/${CurrentRoomId}`)
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log('MessageRoom API');
      });
    // .catch(({ response }) => {
    //   if (response.data.message === 'EXPIRED ACCESS TOKEN') {
    //     reIssueToken(getMessageRoom).catch(() => {
    //       console.log(response, 'getMessageRoom 403 error');
    //       window.location.href = '/login';
    //     });
    //   }
    // });
  };

  const delMessageRoom = async () => {
    await axios.delete(`/messages/rooms/${CurrentRoomId}`).then(() => {
      setCurrentRoomId();
      console.log('현재 대화방 삭제');
    });
    // .catch(({ response }) => {
    //   if (response.data.message === 'EXPIRED ACCESS TOKEN') {
    //     reIssueToken(delMessageRoom).catch(() => {
    //       console.log(response, 'delMessageRoom 403 error');
    //       window.location.href = '/login';
    //     });
    //   }
    // });
>>>>>>> Stashed changes
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <div className={styles.message}>
          <MessageList
            messageList={messageList}
            setMessageList={setMessageList}
            pageInfo={pageInfo}
            setPageInfo={setPageInfo}
          />
          <MessageContent
<<<<<<< Updated upstream
=======
            profile={profile}
>>>>>>> Stashed changes
            messageRoom={messageRoom}
            getMessageList={getMessageList}
            getMessageRoom={getMessageRoom}
            delMessageRoom={delMessageRoom}
          />
        </div>
      </div>
    </div>
  );
};

export default Message;
