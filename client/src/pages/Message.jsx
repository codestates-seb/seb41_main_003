import styles from './Message.module.css';
import MessageList from '../components/Message/MessageList';
import MessageContent from '../components/Message/MessageContent';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useRecoilState } from 'recoil';
import Profile from '../recoil/profile';

const Message = () => {
  const headers = {
    Authorization: sessionStorage.getItem('authorization'),
  };
  const profile = { profileId: 1 };
  // const [profile] = useRecoilState(Profile);
  const [messageList, setMessageList] = useState([]);
  const [messageRoom, setMessageRoom] = useState({});

  //* 원상복구
  // const [currentRoomId, setCurrentRoomId] = useState(
  //   messageList[0].messageRoomId
  // );
  const [currentRoomId, setCurrentRoomId] = useState(3);

  const getMessageList = async () => {
    await axios
      // `${process.env.REACT_APP_BASE_URL}/messages/${profile.profileId}`
      .get(`${process.env.REACT_APP_BASE_URL}/messages/1`, {
        headers: headers,
      })
      .then((res) => {
        setMessageList(res.data.data);
        console.log(res.data.data, 'getMessageList API');
      })
      .catch((err) => console.log(err));
  };

  const getMessageRoom = async () => {
    await axios
      // `${process.env.REACT_APP_BASE_URL}/rooms/${profile.profileId}/${currentRoomId}`
      .get(`${process.env.REACT_APP_BASE_URL}/messages/rooms/1/3`, {
        headers: headers,
      })
      .then((res) => {
        setMessageRoom(res.data.data);
        console.log(res.data.data, 'MessageRoom API');
      })
      .catch((err) => console.log(err, 'getMessageRoom error'));
  };

  const delMessageRoom = async () => {
    await axios
      .delete(
        `${process.env.REACT_APP_BASE_URL}/messages/rooms/${currentRoomId}`,
        {
          headers: headers,
        }
      )
      .then(() => {
        setCurrentRoomId();
        console.log('현재 대화방 삭제');
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getMessageList();
    getMessageRoom();
  }, []);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <h2>메세지함</h2>
        <button onClick={getMessageList}>getMessageList</button>
        <button onClick={getMessageRoom}>getMessageRoom</button>
        <div className={styles.message}>
          <MessageList
            messageList={messageList}
            setCurrentRoomId={setCurrentRoomId}
            currentRoomId={currentRoomId}
          />
          <MessageContent
            messageRoom={messageRoom}
            delMessageRoom={delMessageRoom}
            currentRoomId={currentRoomId}
            headers={headers}
            profile={profile}
            setMessageRoom={setMessageRoom}
          />
        </div>
      </div>
    </div>
  );
};

export default Message;
