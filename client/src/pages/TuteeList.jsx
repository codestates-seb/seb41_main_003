import FeedItem from '../components/MainSection/FeedItem';
import styles from '../pages/TuteeList.module.css';
import { MdSearch, MdFilterList } from 'react-icons/md';
import { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import { ButtonTop } from '../components/Button';
import MenuButtons from '../components/MainSection/FilterButton';
import axios from 'axios';
import useScroll from '../util/useScroll';
import LoadingIndicator from '../components/LoadingIndicator';

const TuteeList = () => {
  const [tuteeData, setTuteeData] = useState([]);
  const [pageInfo, setPageInfo] = useState({
    page: 1,
  });
  const [subjectMenu, setSubjectMenu] = useState([]);
  const [search, setSearch] = useState('');
  const [searchValue, setSearchValue] = useState('');

  const loadingRef = useRef(null);

  const [isLoading, setIsLoading] = useScroll(() => {
    if (pageInfo.page < pageInfo.totalPages - 1) {
      console.log('true');
      setTimeout(() => {
        scrollFunc(pageInfo.page + 1);
        setIsLoading(false);
      }, 500);
    } else setIsLoading(false);
  }, loadingRef);

  const scrollFunc = async (page) => {
    await axios
      .get(
        `/users/tutees?subject=${subjectMenu.join()}&search=${search}&page=${page}`
      )
      .then(({ data }) => {
        console.log(data.pageInfo);
        setTuteeData([...tuteeData, ...data.data]);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  const getTuteeData = async () => {
    await axios
      .get(`/users/tutees?subject=${subjectMenu.join()}&search=${search}`)
      .then(({ data }) => {
        setTuteeData(data.data);
        setPageInfo(data.pageInfo);
      })
      .catch((err) => console.error(err.message));
  };

  const subjectHandler = (e) => {
    if (subjectMenu.includes(e.target.value)) {
      setSubjectMenu(subjectMenu.filter((el) => el !== e.target.value));
    } else {
      setSubjectMenu([e.target.value, ...subjectMenu]);
    }
  };

  const searchHandler = ({ type, key, target }) => {
    if (type === 'change') setSearchValue(target.value);
    else if (target.value === '') setSearch(searchValue);
    else if (type === 'keyup' && key === 'Enter') setSearch(searchValue);
  };

  useEffect(() => {
    getTuteeData();
  }, [subjectMenu, search]);

  return (
    <div className={styles.wrapper}>
      <div className={styles.container}>
        <div className={styles.banner}>
          <div className={styles.bannerContents}>
            <span className={styles.bannerText}>
              나에게 꼭 맞는 비대면 과외를 찾아보세요!
            </span>
            <div className={styles.searchBox}>
              <div className={styles.iconBox}>
                <MdSearch className={styles.mdSearch} />
              </div>
              <input
                className={styles.input}
                placeholder="서울대학교"
                value={searchValue}
                onChange={searchHandler}
                onKeyUp={searchHandler}
              ></input>
            </div>
          </div>
        </div>
        <div className={styles.menuContainer}>
          <MenuButtons
            subjectMenu={subjectMenu}
            subjectHandler={subjectHandler}
          />
          <div className={styles.filter}>
            <MdFilterList className={styles.mdFilterList} />
            <span>최신 순</span>
          </div>
        </div>
        <div className={styles.feedContainer}>
          {tuteeData.map((tutee) => (
            <Link
              to={`/tuteeprofile`}
              state={{ tuteeProfileId: tutee.profileId }}
              key={tutee.profileId}
              className={styles.list}
            >
              <FeedItem data={tutee} userStatus="TUTEE" />
            </Link>
          ))}
          {tuteeData.length === 0 && (
            <div className={styles.notFound}>
              표시 할 튜티가 없습니다. 검색 조건을 확인하세요.
            </div>
          )}
        </div>
        <LoadingIndicator ref={loadingRef} isLoading={isLoading} />
      </div>
      <ButtonTop />
    </div>
  );
};

export default TuteeList;
