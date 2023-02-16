import styles from './MessageList.module.css';
import { useRef } from 'react';
import defaultUser from '../../assets/defaultUser.png';
import PropTypes from 'prop-types';
import axios from 'axios';
import { useSetRecoilState, useRecoilValue } from 'recoil';
import CurrentRoomIdState from '../../recoil/currentRoomId.js';
import LoadingIndicator from '../../components/LoadingIndicator';
import useScroll from '../../util/useScroll';
import Profile from '../../recoil/profile';

const MessageList = ({
  messageList,
  setMessageList,
  pageInfo,
  setPageInfo,
  setIsChat,
}) => {
  const { profileId } = useRecoilValue(Profile);
  const loadingRef = useRef(null);
  const setCurrentRoomId = useSetRecoilState(CurrentRoomIdState);

  const getCurrentRoomId = (e) => {
    setCurrentRoomId(e.currentTarget.id);
    setIsChat(true);
  };

  const [isLoading, setIsLoading] = useScroll(() => {
    if (pageInfo.page < pageInfo.totalPages - 1) {
      setTimeout(() => {
        scrollFunc(pageInfo.page + 1);
        setIsLoading(false);
      }, 100);
    } else setIsLoading(false);
  }, loadingRef);

  //* 리스트가 하단에 도달했을때 호출되는 API
  const scrollFunc = async (page) => {
    await axios
      .get(`/messages/${profileId}?page=${page}`)
      .then((res) => {
        setMessageList([...messageList, ...res.data.data]);
        setPageInfo(res.data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  return (
    <div>
      <ul className={styles.messageList}>
        {messageList.map((list) => {
          return (
            <li key={`roomId${list.messageRoomId}`} className={styles.message}>
              <button
                id={list.messageRoomId}
                onClick={getCurrentRoomId}
                className={styles.person}
              >
                <img
                  src={list.targetProfileUrl || defaultUser}
                  alt="user"
                  className={styles.userImg}
                />
                <div>
                  <h4>{list.targetName}</h4>
                  <p>
                    {list.lastMessage === 'REQ_UEST'
                      ? '매칭 요청'
                      : list.lastMessage === 'MAT_CHING_CAN_CEL'
                      ? '매칭 요청 취소'
                      : list.lastMessage === 'MAT_CHING_CON_FIRM'
                      ? '매칭 요청 승인'
                      : list.lastMessage && list.lastMessage.length > 15
                      ? `${list.lastMessage.slice(0, 15)}...`
                      : list.lastMessage}
                  </p>
                </div>
                {
                  list.lastSenderId !== profileId ? (
                    list.messageStatus === 'UNCHECK' ? (
                      <span className={styles.badge} />
                    ) : undefined // 상대가 보내고 체크된 msg
                  ) : undefined // 내가 보낸 msg
                }
              </button>
            </li>
          );
        })}
        <LoadingIndicator
          ref={loadingRef}
          isLoading={isLoading}
          isSmall={true}
        />
      </ul>
    </div>
  );
};

MessageList.propTypes = {
  messageList: PropTypes.array,
  setMessageList: PropTypes.func,
  pageInfo: PropTypes.object,
  setPageInfo: PropTypes.func,
  setIsChat: PropTypes.func,
};

export default MessageList;
