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

const Journals = ({
  tutoring,
  setTutoring,
  pageInfo,
  setPageInfo,
  tutoringId,
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
              state={{ tutoringId: tutoringId, dateNoticeId: el.dateNoticeId }}
              key={el.dateNoticeId}
            >
              <li className={styles.li}>
                <div className={styles.dateBox}>
                  <span className={styles.day}>
                    {new Date(el.startTime).getDate()}
                  </span>
                  <span className={styles.yearMonth}>{`${new Date(
                    el.startTime
                  ).getFullYear()}년 ${
                    new Date(el.startTime).getMonth() + 1
                  }월`}</span>
                </div>
                <div className={styles.textAndNotiBox}>
                  <div className={styles.textBox}>
                    <span className={styles.goal}>
                      {el.dateNoticeTitle.length > 20
                        ? `학습 목표 | ${el.dateNoticeTitle.slice(0, 30)}... `
                        : `학습 목표 | ${el.dateNoticeTitle}`}
                    </span>
                    <span
                      className={styles.homework}
                    >{`과제 제출 완료 (${el.finishHomeworkCount}/${el.homeworkCount})`}</span>
                  </div>
                  {el.noticeStatus === 'NOTICE' && (
                    <div className={styles.notiIcon}>
                      <HiSpeakerphone className={styles.hiSpeaker} />
                      공지
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
};

export default Journals;
