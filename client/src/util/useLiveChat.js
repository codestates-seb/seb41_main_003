import { useState, useEffect, useRef } from 'react';
import * as SockJS from 'sockjs-client';
// eslint-disable-next-line import/no-unresolved
import * as StompJs from '@stomp/stompjs';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import CurrentRoomIdState from '../recoil/currentRoomId';
import Profile from '../recoil/profile';

const useLiveChat = () => {
  const [text, setText] = useState('');
  const [messageRoom, setMessageRoom] = useState({});
  const CurrentRoomId = useRecoilValue(CurrentRoomIdState);
  const { profileId } = useRecoilValue(Profile);
  const [receiverId, setReceiverId] = useState(0);
  const client = useRef({});

  const URL = 'https://api-tutordiff.site/stomp/content';

  useEffect(() => {
    const setRoom = async () => {
      await initialChatSetting();
      connect();
    };
    setRoom();
    return () => disconnect();
  }, [CurrentRoomId]);

  const initialChatSetting = async () => {
    if (CurrentRoomId !== 0 && CurrentRoomId !== undefined)
      await axios
        .get(`/messages/rooms/${profileId}/${CurrentRoomId}`)
        .then(({ data: { data } }) => {
          if (profileId === data.tuteeId) {
            setReceiverId(data.tutorId);
          } else setReceiverId(data.tuteeId);
          setMessageRoom(data);
        })
        .catch((err) => console.log(err));
  };

  const connect = () => {
    client.current = new StompJs.Client({
      webSocketFactory: () => new SockJS(URL),
      connectHeaders: {
        Authorization: sessionStorage.getItem('authorization'),
      },
      debug: () => {},
      reconnectDelay: 3000,
      heartbeatIncoming: 2000,
      heartbeatOutgoing: 2000,
      onConnect: () => {
        subscribe();
      },
      onStompError: (frame) => {
        console.error(frame);
      },
    });

    client.current.activate();
  };

  const disconnect = () => {
    client.current.deactivate();
  };

  const subscribe = () => {
    client.current.subscribe(`/sub/room/${CurrentRoomId}`, (res) => {
      setMessageRoom((prev) => ({
        ...prev,
        messages: [...prev.messages, JSON.parse(res.body)],
      }));
    });
  };

  const publish = (type = '') => {
    if (!client.current.connected) {
      return;
    }

    let sendText = '';
    switch (type) {
      case '':
        sendText = text;
        break;
      case 'matchingConfirm':
        sendText = 'MAT_CHING_CON_FIRM';
        break;
      case 'matchingCancel':
        sendText = 'MAT_CHING_CAN_CEL';
        break;
    }

    client.current.publish({
      destination: `/pub/chats/${CurrentRoomId}`,
      body: JSON.stringify({
        senderId: profileId,
        receiverId: receiverId,
        messageContent: sendText,
        messageRoomId: CurrentRoomId,
      }),
    });
    setText('');
  };

  return {
    disconnect,
    publish,
    messageRoom,
    setMessageRoom,
    text,
    setText,
  };
};

export default useLiveChat;
