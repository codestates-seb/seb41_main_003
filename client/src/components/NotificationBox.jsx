import styles from './NotificationBox.module.css';
import NoticeItem from './NoticeItem';
import { MdCheck, MdClose } from 'react-icons/md';
import axios from 'axios';
import { useState, useEffect, useRef } from 'react';
import { useRecoilValue } from 'recoil';
import Profile from '../recoil/profile';
import LoadingIndicator from './LoadingIndicator';
import useScroll from '../util/useScroll';
import Loading from '../components/Loading';


const NotificationBox = () => {
  const [isLoadingData, setIsLoadingData] = useState(false);
  const [noticeList, setNoticeList] = useState([0]);
  const [pageInfo, setPageInfo] = useState({
    page: 1,
  });
  const { profileId } = useRecoilValue(Profile);
  const loadingRef = useRef(null);

  const [isLoading, setIsLoading] = useScroll(() => {
    if (pageInfo.page < pageInfo.totalPages - 1) {
      setTimeout(() => {
        scrollFunc(pageInfo.page + 1);
        setIsLoading(false);
      }, 500);
    } else setIsLoading(false);
  }, loadingRef);

  const scrollFunc = async (page) => {
    await axios
      .get(`/alarm/all/${profileId}?page=${page}`)
      .then(({ data }) => {
        setNoticeList([...noticeList, ...data.data]);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  const getNoticeList = async () => {
    setIsLoadingData(true);
    await axios
      .get(`/alarm/all/${profileId}`)
      .then((res) => {
        setNoticeList(res.data.data);
        setPageInfo(res.data.pageInfo);
        setIsLoadingData(false);
      })
      .catch((err) => console.log(err));
  };

  const patchAllAlarm = async () => {
    await axios
      .patch(`/alarm/all/${profileId}`)
      .then(() => {
        setNoticeList((prev) =>
          prev.map((el) => ({ ...el, alarmStatus: 'CHECK' }))
        );
      })
      .catch((err) => console.log(err));
  };

  const deleteAllAlarm = async () => {
    await axios
      .delete(`/alarm/all/${profileId}`)
      .then(() => {
        setNoticeList([]);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getNoticeList();
  }, []);

  return (
    <div className={styles.noticeBoxContainer}>
      <ul className={styles.noticeListArea}>
        {isLoadingData ? (
          <Loading />
        ) : noticeList.length !== 0 ? (
          noticeList?.map((notice) => (
            <div key={notice.alarmId + notice.alarmStatus}>
              <NoticeItem
                data={notice}
                setNoticeList={setNoticeList}
                noticeList={noticeList}
              />
            </div>
          ))
        ) : (
          <div className={styles.noAlarm}>새로운 알림이 없습니다</div>
        )}
        <LoadingIndicator
          ref={loadingRef}
          isLoading={isLoading}
          isSmall={true}
        />
      </ul>
      <div className={styles.buttonArea}>
        <button onClick={patchAllAlarm}>
          <MdCheck size={20} />
          <span>전체 확인</span>
        </button>
        <button onClick={deleteAllAlarm}>
          <MdClose size={20} />
          <span>전체 삭제</span>
        </button>
      </div>
    </div>
  );
};

export default NotificationBox;
