import PropType from 'prop-types';
import { useRef } from 'react';
import { HiSpeakerphone } from 'react-icons/hi';
import styles from './JournalList.module.css';
import { Link } from 'react-router-dom';
import useScroll from '../../util/useScroll';
import Profile from '../../recoil/profile';
import axios from 'axios';
import { useRecoilValue } from 'recoil';
import LoadingIndicator from '../../components/LoadingIndicator';
import dayjs from 'dayjs';

const Journals = ({
  tutoring,
  setTutoring,
  pageInfo,
  setPageInfo,
  tutoringId,
  isFinished,
}) => {
  const loadingRef = useRef(null);
  const { profileId } = useRecoilValue(Profile);

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
      .get(`/tutoring/details/${profileId}/${tutoringId}?page=${page}`)
      .then(({ data }) => {
        setTutoring({
          ...data.data,
          dateNotices: [...tutoring.dateNotices, ...data.data.dateNotices],
        });
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  return (
    <ul className={styles.list}>
      {tutoring.dateNotices?.length === 0 ? (
        <div className={styles.emptyJournals}>
          아직 작성된 과외 일지가 없습니다.
        </div>
      ) : (
        tutoring.dateNotices?.map((el) => {
          return (
            <Link
              to={`/journal`}
              state={{
                tutoringId: tutoringId,
                dateNoticeId: el.dateNoticeId,
                isFinished,
              }}
              key={el.dateNoticeId}
            >
              <li className={styles.li}>
                <div className={styles.dateBox}>
                  <span className={styles.day}>
                    {dayjs(el.startTime).date()}
                    <span className={styles.text}>일</span>
                  </span>
                  <span className={styles.yearMonth}>
                    {dayjs(el.startTime).format('YYYY년 M월')}
                  </span>
                </div>
                <div className={styles.textAndNotiBox}>
                  <div className={styles.textBox}>
                    <div className={styles.goal}>
                      <span>{`학습 목표 | `}</span>
                      <span>
                        {el.dateNoticeTitle.length > 20
                          ? `${el.dateNoticeTitle.slice(0, 18)}... `
                          : `${el.dateNoticeTitle}`}
                      </span>
                    </div>
                    <span
                      className={styles.homework}
                    >{`과제 제출 완료 (${el.finishHomeworkCount}/${el.homeworkCount})`}</span>
                  </div>
                  {el.noticeStatus === 'NOTICE' && (
                    <div className={styles.notiIcon}>
                      <HiSpeakerphone className={styles.hiSpeaker} />
                      <span>공지</span>
                    </div>
                  )}
                </div>
              </li>
            </Link>
          );
        })
      )}
      <LoadingIndicator ref={loadingRef} isLoading={isLoading} isSmall={true} />
    </ul>
  );
};

Journals.propTypes = {
  tutoring: PropType.object,
  setTutoring: PropType.func,
  pageInfo: PropType.object,
  setPageInfo: PropType.func,
  tutoringId: PropType.number,
  isFinished: PropType.bool,
};

export default Journals;
