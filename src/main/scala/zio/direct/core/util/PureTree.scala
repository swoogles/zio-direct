package zio.direct.core.util

import scala.quoted._
import zio.direct.core.metaprog.Trees
import zio.direct.core.metaprog.Extractors._

object PureTree:
  object All:
    def unapply(using Quotes)(tree: List[quotes.reflect.Statement]): Boolean =
      tree.forall(PureTree.unapply(_).isDefined)

  def unapply(using Quotes)(tree: quotes.reflect.Tree): Option[quotes.reflect.Tree] =
    import quotes.reflect._
    Trees.exists(tree, Symbol.spliceOwner) {
      case RunCall(_) => true
    } match {
      case true  => None
      case false => Some(tree)
    }