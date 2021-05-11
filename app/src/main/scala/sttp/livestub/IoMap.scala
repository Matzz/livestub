package sttp.livestub

import cats.effect.IO
import cats.effect.concurrent.Ref

class IoMap[K, V](ref: Ref[IO, Map[K, V]]) {

  def put(k: K, v: V): IO[Unit] = {
    ref.update(m => m + (k -> v))
  }

  def get(k: K): IO[Option[V]] = {
    ref.get.map(_.get(k))
  }

  def clear(): IO[Unit] = {
    ref.set(Map())
  }

  def collect[R](f: PartialFunction[(K, V), R]): IO[List[R]] = {
    ref.get.map(m => m.collect(f).toList)
  }

  def getAll: IO[Map[K, V]] = ref.get
}
