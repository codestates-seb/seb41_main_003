import { BlueSubject } from '../Subject.jsx';
import { MdStar, MdTextsms, MdStarOutline } from 'react-icons/md';
import styles from './ProfileContents.module.css';
import PropType from 'prop-types';
import { useRef } from 'react';

const TutorContents = ({ user }) => {
  const {
    difference,
    way,
    subjects,
    gender,
    character,
    pay,
    wantDate,
    preTutoring,
    reviews,
  } = user;

  const reviewRef = useRef(null);

  const starMaker = (key) => {
    return new Array(5)
      .fill(0)
      .map((el, idx) =>
        idx + 1 <= key ? <MdStar key={idx} /> : <MdStarOutline key={idx} />
      );
  };

  return (
    <section className={styles.container}>
      <div className={styles.way}>
        <p className={styles.font4}>수업방식</p>
        <p className={styles.paragragh}>{way}</p>
      </div>
      <div>
        <div className={styles.subject}>
          <p className={styles.font4}>과목별 수업 내용</p>
          {subjects.map(({ subjectId, subjectTitle, content }) => {
            return (
              <div className={styles.subjectList} key={subjectId}>
                <BlueSubject text={subjectTitle} />
                <p className={styles.paragragh}>{content}</p>
              </div>
            );
          })}
        </div>
      </div>
      <div>
        <p className={styles.font4}>차별점</p>
        <p className={styles.paragragh}>{difference}</p>
      </div>
      <div>
        <p className={styles.font4}>성별</p>
        <p className={styles.paragragh}>{gender}</p>
      </div>
      <div>
        <p className={styles.font4}>성격</p>
        <p className={styles.paragragh}>{character}</p>
      </div>
      <div>
        <p className={styles.font4}>수업료</p>
        <p className={styles.paragragh}>{pay}</p>
      </div>
      <div>
        <p className={styles.font4}>과외 가능 요일 / 시간</p>
        <p className={styles.paragragh}>{wantDate}</p>
      </div>
      <div>
        <p className={styles.font4}>시범 과외 가능 여부</p>
        <p className={styles.paragragh}>{preTutoring}</p>
      </div>
      <div className={styles.reviewContainer} ref={reviewRef}>
        <div className={styles.reviewTitleLine}>
          <p className={styles.font3}>후기</p>
          <p className={styles.font5}>총 {reviews.length}개의 후기</p>
        </div>
        <hr />
        <div>
          {reviews.map((review) => {
            const {
              professional,
              readiness,
              explanation,
              punctuality,
              tuteeName,
              reviewBody,
              reviewId,
            } = review;
            return (
              <div key={reviewId} className={styles.reviewContentBox}>
                <p className={styles.reviewProfileId}>{tuteeName}</p>
                <div className={styles.rating}>
                  <div className={styles.flexbox}>
                    <p>
                      전문성
                      <span className={styles.stars}>
                        {starMaker(professional)}
                      </span>
                    </p>
                    <p>
                      준비성
                      <span className={styles.stars}>
                        {starMaker(readiness)}
                      </span>
                    </p>
                  </div>
                  <div className={styles.flexbox}>
                    <p>
                      설명력
                      <span className={styles.stars}>
                        {starMaker(explanation)}
                      </span>
                    </p>
                    <p>
                      시간 준수
                      <span className={styles.stars}>
                        {starMaker(punctuality)}
                      </span>
                    </p>
                  </div>
                </div>
                <p className={styles.reviewBody}>{reviewBody}</p>
              </div>
            );
          })}
        </div>
      </div>
      <button
        className={styles.reviewBtn}
        onClick={() =>
          reviewRef.current.scrollIntoView({
            behavior: 'smooth',
            block: 'start',
          })
        }
      >
        과외 후기 <MdTextsms />
      </button>
    </section>
  );
};
TutorContents.propTypes = {
  user: PropType.object,
};

export default TutorContents;
