import styles from './StarRating.module.css';
import { MdStar, MdStarOutline } from 'react-icons/md';
import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';

const StarRating = ({ name, reviewData, setReviewData }) => {
  const [clicked, setClicked] = useState([false, false, false, false, false]);

  const starArr = [0, 1, 2, 3, 4];

  const handleStarClick = (index) => {
    let clickStates = [...clicked];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setClicked(clickStates);
  };

  const getStarRate = (name) => {
    let score = clicked.filter(Boolean).length;
    setReviewData({
      ...reviewData,
      [name]: score,
    });
  };

  useEffect(() => {
    getStarRate(name);
  }, [clicked]);

  return (
    <div>
      {starArr.map((el, idx) => {
        return clicked[el] ? (
          <MdStar
            className={styles.star}
            role="button"
            key={idx}
            onClick={() => handleStarClick(el)}
          />
        ) : (
          <MdStarOutline
            className={styles.star}
            role="button"
            key={idx}
            onClick={() => {
              handleStarClick(el);
            }}
          />
        );
      })}
    </div>
  );
};

StarRating.propTypes = {
  reviewData: PropTypes.object,
  setReviewData: PropTypes.func,
  name: PropTypes.string,
};

export default StarRating;
